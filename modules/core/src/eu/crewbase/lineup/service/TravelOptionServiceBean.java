package eu.crewbase.lineup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EmailInfo;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TripDTO;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.TravelOptionStatus;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

@Service(TravelOptionService.NAME)
public class TravelOptionServiceBean implements TravelOptionService {

	private static final Logger log = LoggerFactory.getLogger(TravelOptionServiceBean.class);

	@Inject
	private Persistence persistence;
	@Inject
	private DataManager dataManager;
	@Inject
	private Metadata metadata;

	@Inject
	public EmailService emailService;

	@Override
	public void createTravelOption(Transfer transfer) {

		Transfer transferWithFavTrips = getTransferWithIntegratedFavoriteTrips(transfer);
		if (transferWithFavTrips != null) {

			for (FavoriteTrip favoriteTrip : getFavoriteTripsBySiteList(transferWithFavTrips.getSites())) {
				if (getFreeCapacityForTrip(favoriteTrip, transferWithFavTrips, transfer).getBookedSeats() > 0) {
					TravelOption travelOption = metadata.create(TravelOption.class);
					User user = getUserByUsername(favoriteTrip.getCreatedBy());
					if (user == null) {
						// qwe user
						user = dataManager.load(User.class).query("select u from sec$User u where u.login = 'qwe'")
								.one();
					}
					travelOption.setFavoriteTrip(favoriteTrip);
					travelOption.setTransfer(transfer);
					travelOption.setStatus(TravelOptionStatus.Init);
					dataManager.commit(travelOption);
				}
			}
		}
	}

	@Override
	public void sendTravelOptionNotification() {
		try (Transaction tx = persistence.createTransaction()) {
			@SuppressWarnings("unchecked")
			List<TravelOption> optionList = persistence.getEntityManager()
					.createQuery(
							"select m from lineup$TravelOption m where m.status = :status and m.favoriteTrip.sendSummery = false")
					.setParameter("status", TravelOptionStatus.Init).getResultList();

			for (TravelOption travelOption : optionList) {
				String email = getEmailByUserLogin(travelOption.getFavoriteTrip().getCreatedBy());
				EmailInfo mail = new EmailInfo(email, "Testmessage", travelOption.getTransfer().toString());

				emailService.sendEmailAsync(mail);
				travelOption.setStatus(TravelOptionStatus.Sent);
				persistence.getEntityManager().persist(travelOption);
				tx.commit();
			}
		}
	}

	@Override
	public List<Site> getReachableSites(UUID transferId) {
		List<Site> siteResultList = null;
		try (Transaction tx = persistence.createTransaction()) {

			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			siteResultList = getReachableSites(transfer);

			// jeweils eine noch nicht vorhandene hinzufügen und beste Route
			// berechnen, ist die beste Route kürzer als Range?
			// dann hinzufügen mit delta in km um zus. Kosten darzustellen
			// wie lang darf der Umweg für die PAX sein? Wenn keiner drin sitzt?
			// nur hinzufügen, wenn zus. Strecke kürzer als Weg Hin/Rück allein

		}
		return siteResultList;
	}

	public List<FavoriteTrip> getFavoriteTripsBySiteList(List<Site> siteList) {
		// Schnittmenge Favoriten und Sites des Transfers

		List<UUID> collect = siteList.stream().map(Site::getId).collect(Collectors.toList());

		return dataManager.load(FavoriteTrip.class)
				.query("select f from lineup$FavoriteTrip f where f.startSite.id in :siteList and f.destination.id in :siteList")
				.parameter("siteList", collect).list();

	}

	/**
	 * Es werden die Tickets gruppiert nach Sites selektiert -> Tickets bilden
	 * Strecken ab mit entsprechenden gebuchten Plätzen
	 * 
	 */
	@SuppressWarnings("unchecked")
	public TripDTO getFreeCapacityForTrip(FavoriteTrip desiredTrip, Transfer transferWithFavTrips, Transfer transfer) {

		List<TripDTO> groupedTickets = getGroupedTickets(transfer);
		HashMap<UUID, Integer> capaMap = getBookedSeatsMap(groupedTickets, transferWithFavTrips);

		// dann über Fav-Strecke und Result iterieren
		int capa = transfer.getCraftType().getSeats();
		capa = 12;
		int minAvailable = capa;
		// den kompletten Transfer durchlaufen und die minimale Kapazität auf
		// der desiredRoute ermitteln
		for (Site favSite : transferWithFavTrips.getSites()) {
			boolean onboard = false;
			if (favSite.getId().equals(desiredTrip.getStartSite().getId())) {
				onboard = true;
			}
			if (favSite.getId().equals(desiredTrip.getDestination().getId())) {
				onboard = false;
			}
			if (onboard && capaMap.get(favSite.getId()) != null && capaMap.get(favSite.getId()) < minAvailable) {
				minAvailable = minAvailable - capaMap.get(favSite.getId());
			}
		}

		TripDTO tmp = new TripDTO();
		tmp.setBookedSeats(minAvailable);
		tmp.setSiteA(desiredTrip.getStartSite());
		tmp.setSiteB(desiredTrip.getDestination());
		tmp.setTransfer(transfer);
		log.info(tmp.toString());
		return tmp;
	}

	public HashMap<UUID, Integer> getBookedSeatsMap(List<TripDTO> groupedTickets, Transfer transferWithFavTrips) {

		HashMap<UUID, Integer> resultCapaMap = new HashMap<UUID, Integer>();

		// über die tickets iterieren

		for (TripDTO ticketGroup : groupedTickets) {
			log.debug("Tickets " + ticketGroup.getSiteA().getItemDesignation() + " - "
					+ ticketGroup.getSiteB().getItemDesignation() + ": " + ticketGroup.getBookedSeats());

			// capaMap enthält booked seats beim Verlassen der Site:
			// über die Strecke iterieren, wenn site = startSite -> PAX steigen
			// zu, wenn Site = destination -> PAX steigen aus
			boolean onboard = false;
			
			
			// den letzten Waypoint nicht mehr berücksichtigen
			for (int i = 0; i < transferWithFavTrips.getWaypoints().size() - 1; i++) {
				Waypoint waypoint = transferWithFavTrips.getWaypoints().get(i);
				Site site = waypoint.getSite();

				if (site.getId().equals(ticketGroup.getSiteB().getId()) && onboard) {
					onboard = false;
				} else if (site.getId().equals(ticketGroup.getSiteA().getId()) || onboard) {
					int currentSeats = 0;
					if (resultCapaMap.containsKey(site.getId())) {
						currentSeats = resultCapaMap.get(site.getId());
					}
					log.debug("Site: " + site.getItemDesignation() + " Seats to add: " + ticketGroup.getBookedSeats());
					resultCapaMap.put(site.getId(), currentSeats + ticketGroup.getBookedSeats());
					onboard = true;
				}
			}
		}
		return resultCapaMap;
	}

	public List<TripDTO> getGroupedTickets(Transfer transfer) {
		List<TripDTO> resultList = new ArrayList<TripDTO>();
		try (Transaction tx = persistence.createTransaction()) {
			List<Object[]> ticketList = persistence.getEntityManager()
					.createQuery("SELECT t.startSite AS siteA, t.destinationSite, COUNT(t) AS total "
							+ "FROM lineup$Ticket t where t.transfer.id = :transferId "
							+ "GROUP BY t.startSite, t.destinationSite ORDER BY t.startSite.itemDesignation, t.destinationSite.itemDesignation ASC")
					.setParameter("transferId", transfer.getId()).getResultList();

			for (Object[] result : ticketList) {
				TripDTO tmp = new TripDTO();
				tmp.setSiteA((Site) result[0]);
				tmp.setSiteB((Site) result[1]);
				tmp.setBookedSeats(((Number) result[2]).intValue());
				resultList.add(tmp);
			}
		}
		return resultList;
	}

	public List<Site> getReachableSites(Transfer transfer) {
		List<Site> siteResultList;
		siteResultList = new ArrayList<Site>();
		CraftType craft = transfer.getCraftType();

		List<UUID> collect = transfer.getSites().stream().map(Site::getId).collect(Collectors.toList());

		List<Site> siteList = dataManager.load(Site.class)
				.query("select s from lineup$Site s where s.id not in :transferSiteList")
				.parameter("transferSiteList", collect).list();

		// welche Sites können noch erreicht werden?
		// - Liste der vorhandenen Sites übergeben

		// jede site an jeder position einfügen und Gesamtdistanz ausrechnen
		log.debug("Vor Site add/remove " + transfer.getRouteShort());
		for (Site site : siteList) {
			Waypoint addedWaypoint = createWaypoint(site);
			if (transfer.addWaypointShortestWay(addedWaypoint)) {
				siteResultList.add(site);
				transfer.remove(addedWaypoint);
			}
			log.debug("Nachdem Site add/remove " + transfer.getRouteShort());
		}
		return siteResultList;
	}

	private Waypoint createWaypoint(Site site) {
		// Neuen WP erstellen
		Waypoint waypoint = metadata.create(Waypoint.class);
		waypoint.setSite(site);
		return waypoint;
	}

	public Transfer getTransferWithIntegratedFavoriteTrips(Transfer transfer) {

		// Im Transfer sowie schon enthaltene Sites
		List<Site> allPossibleSitesList = transfer.getSites();

		// welche können noch zusätzlich erreicht werden
		allPossibleSitesList.addAll(getReachableSites(transfer));

		List<FavoriteTrip> favList = getFavoriteTripsBySiteList(allPossibleSitesList);

		for (FavoriteTrip favoriteTrip : favList) {
			// nur den Start einbauen
			Log.info("Start Site einbauen: " + favoriteTrip.getStartSite().getSiteName());

			if (transfer.addWaypointShortestWay(createWaypoint(favoriteTrip.getStartSite()))) {
				Log.info("Transfer mit StartSite sieht so aus: " + transfer.getRouteShort());
				// in den neuen Transfer die zweite Site einbauen

				if (transfer.addWaypointShortestWay(createWaypoint(favoriteTrip.getDestination()))) {
					Log.info("Transfer mit DestSite sieht so aus: " + transfer.getRouteShort());
				}
				return transfer;
			}
		}
		return null;
	}

	private String getEmailByUserLogin(String createdBy) {
		String one = dataManager.loadValue("select email from sec$User u where u.login = :login", String.class).one();
		return one;
	}

	private User getUserByUsername(String createdBy) {
		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from sec$User e where e.login = :login";

			return persistence.getEntityManager().createQuery(queryString, User.class).setParameter("login", createdBy)
					.getFirstResult();
		}
	}

	private String getNotificationMessage(FavoriteTrip favoriteTrip, Transfer transfer) {
		String message = "Sehr geehrte Dame und Herren \n\n";
		message += "auf Ihrer gewünschten Strecke wurden freie Plätze gemeldet: \n\n";
		message += transfer.toString() + "\n\n";

		return message;
	}

	@Override
	public void bookSeats(TravelOption entity) {
		entity.getTransfer().bookTravelOption(entity);
	}
}