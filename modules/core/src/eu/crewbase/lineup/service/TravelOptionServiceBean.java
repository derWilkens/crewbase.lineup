package eu.crewbase.lineup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EmailInfo;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;

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
	public Persistence persistence;
	@Inject
	public DataManager dataManager;
	@Inject
	public Metadata metadata;

	@Inject
	public EmailService emailService;
	@Inject
	public TransferService transferService;

	@Override
	public List<TravelOption> createTravelOptions(UUID transferId) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			List<TravelOption> missingTravelOptions = null;
			if (transfer != null) {
				missingTravelOptions = createMissingTravelOptions(transfer);
				tx.commit();
			}
			return missingTravelOptions;
		}
	}

	/**
	 * Zu einem Transfer werden für alle TravelOptions die noch verfügbaren
	 * Seats neu berechnet
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TravelOption> updateTravelOptions(UUID transferId) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			List<TravelOption> travelOptions = null;
			if (transfer != null) {
				// die Seats der bestehenden Optionen werden aktualiert
				travelOptions = updateAvailableSeats(transfer);

				// fehlende Optionen werden mit aktuellen verfügbaren Seats
				// erstellt.
				travelOptions.addAll(createMissingTravelOptions(transfer));
				tx.commit(); // schlägt fehl weil die Site e1 geupdated werden --> gerade nicht
								// soll, aber zwischenzeitlich anderweitig
								// aktualisiert wurde.
			}
			return travelOptions;
		}
	}

	@SuppressWarnings("unchecked")
	private List<TravelOption> updateAvailableSeats(Transfer transfer) {
		List<TravelOption> travelOptions = persistence.getEntityManager()
				.createQuery("select t from lineup$TravelOption t where t.transfer.id = :transferId")
				.setParameter("transferId", transfer.getId()).getResultList();

		for (TravelOption travelOption : travelOptions) {
			travelOption.setAvailableSeats(getGroupedTickets(transfer));
			persistence.getEntityManager().persist(travelOption);
		}
		return travelOptions;
	}

	@SuppressWarnings("unchecked")
	private List<TravelOption> createMissingTravelOptions(Transfer transfer) {

		List<TravelOption> existingTravelOptionList = persistence.getEntityManager()
				.createQuery("select t from lineup$TravelOption t where t.transfer.id = :transferId")
				.setParameter("transferId", transfer.getId()).getResultList();
		log.debug("Exiting TravelOptions: " + existingTravelOptionList.size() + " " + transfer.getRoute());
		
		Transfer transferWithFavTripSites = getTransferWithIntegratedFavoriteTrips(transfer);

		List<FavoriteTrip> favoriteTrips = getFavoriteTripsBySiteList(transferWithFavTripSites.getSiteHash());

		for (TravelOption existingTravelOption : existingTravelOptionList) {
			favoriteTrips.remove(existingTravelOption.getFavoriteTrip());
		}
		
		log.debug("New fitting FavoriteTrips: " + favoriteTrips.size() + ", " + transfer.getRoute());
		List<TravelOption> resultList = new ArrayList<>();
		for (FavoriteTrip favoriteTrip : favoriteTrips) {
			resultList.add(persistTravelOption(transfer, transferWithFavTripSites, favoriteTrip));
		}
		return resultList;
	}

	private TravelOption persistTravelOption(Transfer originalTransfer, Transfer transferWithFavTrips,
			FavoriteTrip favoriteTrip) {
		TravelOption travelOption = metadata.create(TravelOption.class);
		User user = getUserByUsername(favoriteTrip.getCreatedBy());
		if (user == null) {
			// qwe user
			user = dataManager.load(User.class).query("select u from sec$User u where u.login = 'qwe'").one();
		}
		travelOption.setFavoriteTrip(favoriteTrip);
		travelOption.setTransfer(originalTransfer);
		travelOption.setStatus(TravelOptionStatus.Init);
		travelOption.setBookedSeats(
				transferWithFavTrips.getFreeCapacityForTrip(getGroupedTickets(transferWithFavTrips), favoriteTrip));
		travelOption.setAvailableSeats(transferWithFavTrips.getCraftType().getSeats() - travelOption.getBookedSeats());
		persistence.getEntityManager().persist(travelOption);
		log.debug("TavelOption created: " + favoriteTrip.toString());
		return travelOption;
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
	public void bookSeats(UUID travelOptionId, int bookedSeats) {
		TravelOption travelOption = dataManager.load(TravelOption.class).id(travelOptionId).one();
		travelOption.setStatus(TravelOptionStatus.Requested);
		travelOption.setBookedSeats(bookedSeats);
		dataManager.commit(travelOption);
	}

	/**
	 * Die Buchung der Sitzplätze wird in der Traveloption bestätigt die
	 * available Seats der weiteren TravelOptions werden aktualisiert
	 */
	@Override
	public void approveBooking(UUID travelOptionId) {
		TravelOption travelOption;
		try (Transaction tx = persistence.createTransaction()) {
			travelOption = persistence.getEntityManager().find(TravelOption.class, travelOptionId);
			travelOption.setStatus(TravelOptionStatus.Approved);
			persistence.getEntityManager().persist(travelOption);
			tx.commit();
		}
		// Tickets für transfer erstellen
		transferService.createTickets(travelOption.getId());

		// TravelOptionen aktualisieren
		// updateTravelOptions(travelOption.getTransfer().getId());

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

	private List<FavoriteTrip> getFavoriteTripsBySiteList(HashMap<UUID, Site> siteHash) {
		// Schnittmenge Favoriten und Sites des Transfers

		Set<UUID> collect = siteHash.keySet(); // stream().map(Site::getId).collect(Collectors.toList());
		return persistence.getEntityManager().createQuery("select f from lineup$FavoriteTrip f where f.startSite.id in :siteList and f.destination.id in :siteList")
				.setParameter("siteList", collect)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<TripDTO> getGroupedTickets(Transfer transfer) {
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

	private Map<UUID, Site> getReachableSitesHash(Transfer transfer) {
		Map<UUID, Site> resultHash = new HashMap<UUID, Site>();
		List<Site> siteResultList = getReachableSites(transfer);
		for (Site site : siteResultList) {
			log.debug("ReachableSite: " + site.getItemDesignation());
			resultHash.put(site.getId(), site);
		}
		return resultHash;
	}

	@SuppressWarnings("unchecked")
	public List<Site> getReachableSites(Transfer transfer) {
		List<Site> siteResultList;
		siteResultList = new ArrayList<Site>();

		List<UUID> collect = transfer.getSites().stream().map(Site::getId).collect(Collectors.toList());

		List<Site> siteList = persistence.getEntityManager()
				.createQuery("select s from lineup$Site s where s.id not in :transferSiteList")
				.setParameter("transferSiteList", collect).getResultList();

		// welche Sites können noch erreicht werden?
		// - Liste der vorhandenen Sites übergeben

		// jede site an jeder position einfügen und Gesamtdistanz ausrechnen
		log.debug("Vor Site add/remove " + transfer.getRouteShort());
		for (Site site : siteList) {
			Waypoint addedWaypoint = createWaypoint(site);
			if (transfer.addWaypointShortestWay(addedWaypoint)) {
				siteResultList.add(site);
				transfer.getWaypoints().remove(addedWaypoint);
				transfer.renumber();
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

	private Transfer getTransferWithIntegratedFavoriteTrips(Transfer transfer) {
		Transfer resultTransfer = new Transfer();
		resultTransfer.setCraftType(transfer.getCraftType());
		for (Waypoint waypoint : transfer.getWaypoints()) {
			Waypoint tempWp = new Waypoint();
			tempWp.setSite(waypoint.getSite());
			tempWp.setTransfer(resultTransfer);
			resultTransfer.getWaypoints().add(tempWp);
		}

		// die im Transfer sowie schon enthaltene Sites
		// List<Site> allPossibleSitesList = transfer.getSites();
		HashMap<UUID, Site> siteHash = transfer.getSiteHash();

		// welche können noch zusätzlich erreicht werden
		// allPossibleSitesList.addAll(getReachableSites(transfer));
		siteHash.putAll(getReachableSitesHash(transfer));

		List<FavoriteTrip> favList = getFavoriteTripsBySiteList(siteHash);

		for (FavoriteTrip favoriteTrip : favList) {

			// nur den Start einbauen
			log.debug("Start Site einbauen: " + favoriteTrip.getStartSite().getSiteName());

			if (resultTransfer.addWaypointShortestWay(createWaypoint(favoriteTrip.getStartSite()))) {
				log.debug("Transfer mit StartSite sieht so aus: " + resultTransfer.getRouteShort());
				// in den neuen Transfer die zweite Site einbauen

				if (resultTransfer.addWaypointShortestWay(createWaypoint(favoriteTrip.getDestination()))) {
					log.debug("Transfer mit DestSite sieht so aus: " + resultTransfer.getRouteShort());
				}
			}
		}
		return resultTransfer;
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
	public void declineBooking(UUID travelOptionId) {
		TravelOption travelOption;
		try (Transaction tx = persistence.createTransaction()) {
			travelOption = persistence.getEntityManager().find(TravelOption.class, travelOptionId);
			travelOption.setStatus(TravelOptionStatus.Rejected);
			persistence.getEntityManager().persist(travelOption);
			tx.commit();
		}
		
	}

}