package eu.crewbase.lineup.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.dto.TripDTO;
import eu.crewbase.lineup.entity.wayfare.AnchorWaypoint;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Standstill;
import eu.crewbase.lineup.entity.wayfare.Ticket;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

@Service(CrewChangeService.NAME)
public class CrewChangeServiceBean implements CrewChangeService {
	private static final Logger log = LoggerFactory.getLogger(CrewChangeServiceBean.class);

	@Inject
	private Persistence persistence;
	@Inject
	private DataManager dataManager;
	@Inject
	private Metadata metadata;

	@Override
	public UUID createCrewChange(CrewChangeCreateDTO dto) {
		CrewChange cc = null;
		cc = dataManager.create(CrewChange.class);
		cc.setStartDate(dto.getStartDateTime());

		/**
		 * Transfer anlegen und verknüpfen
		 */
		Transfer transfer = dataManager.create(Transfer.class);
		transfer.setTransferOrderNo(1);
		transfer.setCrewChange(cc);
		transfer.setCraftType(dto.getHelicopterType());

		/**
		 * 2 Standard-Waypoints anlegen A - B - (A) Endpunkt ist implizit
		 */
		AnchorWaypoint awp1 = dataManager.create(AnchorWaypoint.class);
		awp1.setSite(dto.getDepartureSite());
		awp1.setStartDateTime(dto.getStartDateTime());
		awp1.setTransfer(transfer);
		transfer.setAnchorWaypoint(awp1);
		// transfer.setNextWaypoint(awp1);

		Waypoint wp1 = dataManager.create(Waypoint.class);
		wp1.setSite(dto.getArrivalSite());
		wp1.setTransfer(transfer);
		// wp1.setNextWaypoint(awp1); geht nicht, weil der letzte Punkt
		// grundsätzlich der erste ist - das ist aber ein Problem

		awp1.setNextWaypoint(wp1);
		awp1.setStartDateTime(dto.getStartDateTime());
		wp1.setPreviousStandstill(awp1);
		cc.getTransfers().add(transfer);

		createTickets(dto.getOccupiedSeatsWay1(), dto.getDepartureSite(), dto.getArrivalSite(), transfer);
		createTickets(dto.getOccupiedSeatsWay2(), dto.getArrivalSite(), dto.getDepartureSite(), transfer);

		dataManager.commit(cc);
		return cc.getId();

	}

	private UUID tmp(CrewChangeCreateDTO dto) {

		CrewChange cc = null;
		try (Transaction tx = persistence.createTransaction()) {

			/**
			 * Crew Change
			 */
			cc = (CrewChange) metadata.create(CrewChange.class);
			cc.setStartDate(dto.getStartDateTime());

			/**
			 * Transfer anlegen und verknüpfen
			 */
			Transfer transfer = metadata.create(Transfer.class);
			transfer.setTransferOrderNo(1);
			transfer.setCrewChange(cc);
			transfer.setCraftType(dto.getHelicopterType());

			/**
			 * 2 Standard-Waypoints anlegen A - B - (A) Endpunkt ist implizit
			 */
			AnchorWaypoint awp1 = metadata.create(AnchorWaypoint.class);
			awp1.setSite(dto.getDepartureSite());
			awp1.setStartDateTime(dto.getStartDateTime());
			awp1.setTransfer(transfer);
			transfer.setAnchorWaypoint(awp1);
			// transfer.setNextWaypoint(awp1);

			Waypoint wp1 = metadata.create(Waypoint.class);
			wp1.setSite(dto.getArrivalSite());
			wp1.setTransfer(transfer);
			// wp1.setNextWaypoint(awp1); geht nicht, weil der letzte Punkt
			// grundsätzlich der erste ist - das ist aber ein Problem

			awp1.setNextWaypoint(wp1);
			awp1.setStartDateTime(dto.getStartDateTime());
			wp1.setPreviousStandstill(awp1);
			cc.getTransfers().add(transfer);

			createTickets(dto.getOccupiedSeatsWay1(), dto.getDepartureSite(), dto.getArrivalSite(), transfer);
			createTickets(dto.getOccupiedSeatsWay2(), dto.getArrivalSite(), dto.getDepartureSite(), transfer);

			persistence.getEntityManager().persist(cc);
			tx.commit();
		}
		return cc.getId();
	}

	private void createTickets(int amount, Site siteA, Site siteB, Transfer transfer) {
		for (int i = 0; i < amount; i++) {
			Ticket ticket = metadata.create(Ticket.class);
			ticket.setTransfer(transfer);
			ticket.setStartSite(siteA);
			ticket.setDestinationSite(siteB);
			transfer.getTickets().add(ticket);
		}

	}

	@Override
	public Waypoint addWaypoint(UUID transferId, UUID siteId, UUID prevStandstillId) {

		Transfer transfer = null;
		Waypoint waypoint = null;
		try (Transaction tx = persistence.createTransaction()) {
			transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			Site site = persistence.getEntityManager().find(Site.class, siteId);
			Standstill prevStandstill = persistence.getEntityManager().find(Standstill.class, prevStandstillId);
			waypoint = createWaypoint(transfer, site, prevStandstill);
			tx.commit();
		}
		return waypoint;
	}

	private Waypoint createWaypoint(Transfer transfer, Site site, Standstill prevStandstill) {

		// Neuen WP erstellen
		Waypoint waypoint = metadata.create(Waypoint.class);
		waypoint.setSite(site);
		linkWaypoint(waypoint, prevStandstill);
		return waypoint;
	}

	private void linkWaypoint(Waypoint waypoint, Standstill prevStandstill) {
		Waypoint newNext = prevStandstill.getNextWaypoint();
		waypoint.setNextWaypoint(newNext);
		waypoint.setPreviousStandstill(prevStandstill);

		// Nachfolger auf newWP setzen
		if (null != newNext) {
			newNext.setPreviousStandstill(waypoint);
		}

		// Vorgänger auf newWP setzen
		prevStandstill.setNextWaypoint(waypoint);
	}

	@Override
	public void moveWaypoint(UUID waypointId, UUID newPrevStandstill) {
		try (Transaction tx = persistence.createTransaction()) {
			Waypoint waypoint = persistence.getEntityManager().find(Waypoint.class, waypointId);
			Standstill prevStandstill = persistence.getEntityManager().find(Standstill.class, newPrevStandstill);

			unlinkWaypoint(waypoint);
			linkWaypoint(waypoint, prevStandstill);

			tx.commit();
		}
	}

	@Override
	public void removeWaypoint(UUID waypointId) {
		try (Transaction tx = persistence.createTransaction()) {
			Waypoint waypoint = persistence.getEntityManager().find(Waypoint.class, waypointId);
			unlinkWaypoint(waypoint);
			persistence.getEntityManager().remove(waypoint);
			tx.commit();
		}
	}

	private void unlinkWaypoint(Waypoint waypoint) {
		waypoint.getPreviousStandstill().setNextWaypoint(waypoint.getNextWaypoint());
		if (null != waypoint.getNextWaypoint()) {
			waypoint.getNextWaypoint().setPreviousStandstill(waypoint.getPreviousStandstill());
		}
	}

	@Override
	public List<Site> getReachableSites(UUID transferId) {
		List<Site> siteResultList = new ArrayList<Site>();
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			// List<Waypoint> entSites =
			// persistence.getEntityManager().createQuery("select w from
			// lineup$Waypoint w where w.transfer.Id = '" +
			// transfer.getId()+"'", Waypoint.class).getResultList();
			CraftType craft = transfer.getCraftType();
			List<Site> siteList = persistence.getEntityManager().createQuery("select s from lineup$Site s", Site.class)
					.getResultList();
			craft = metadata.create(CraftType.class);
			craft.setMaxRange(10000);

			// welche Sites können noch erreicht werden?
			// - Liste der vorhandenen Sites übergeben

			// jede site an jeder position einfügen und Gesamtdistanz ausrechnen

			for (Site site : siteList) {
				Standstill currentStandstill = transfer.getAnchorWaypoint();

				do {
					Waypoint nextOriginalWaypoint = currentStandstill.getNextWaypoint();
					Waypoint addedWaypoint = createWaypoint(transfer, site, currentStandstill);
					currentStandstill = nextOriginalWaypoint;
					Log.info(transfer.getRoute() + " Dist: " + transfer.getTotalDistance());
					if (transfer.getTotalDistance() < craft.getMaxRange()) {
						siteResultList.add(site);
						unlinkWaypoint(addedWaypoint);
						break;
					}
					unlinkWaypoint(addedWaypoint);
				} while (currentStandstill != null);
			}

			// jeweils eine noch nicht vorhandene hinzufügen und beste Route
			// berechnen, ist die beste Route kürzer als Range?
			// dann hinzufügen mit delta in km um zus. Kosten darzustellen
			// wie lang darf der Umweg für die PAX sein? Wenn keiner drin sitzt?
			// nur hinzufügen, wenn zus. Strecke kürzer als Weg Hin/Rück allein

		}
		return siteResultList;
	}

	/**
	 * Liefert zukünftige mögliche Mitfluggelegenheiten auf Basis meiner
	 * Favoriten
	 */
	public List<TripDTO> getMyTrips(Date dateRangeStart, Date dateRangeEnd) {
		List<TripDTO> resultList = new ArrayList<TripDTO>();

		// Zukünftige CrewChanges und meine Favoriten holen
		try (Transaction tx = persistence.createTransaction()) {

			if(dateRangeStart == null){
				dateRangeStart = new Date();
			}
			if(dateRangeEnd == null){
				//dateRangeEnd = Calendar.getInstance()
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_YEAR, 1);
				dateRangeEnd = c.getTime();
			}
			List<CrewChange> ccList = persistence.getEntityManager()
					.createQuery(
							"select c from lineup$CrewChange c where c.startDate >= :dateRangeStart and c.startDate <= :dateRangeEnd",
							CrewChange.class)
					.setParameter("dateRangeStart", dateRangeStart).setParameter("dateRangeEnd", dateRangeEnd)
					.getResultList();

			List<FavoriteTrip> favList = persistence.getEntityManager()
					.createQuery("select f from lineup$FavoriteTrip f where f.createdBy = :createdBy",
							FavoriteTrip.class)
					.setParameter("createdBy", "test_admin").getResultList();
			favList.get(0).getStartSite();

			for (FavoriteTrip favoriteTrip : favList) {
				favoriteTrip = persistence.getEntityManager().find(FavoriteTrip.class, favoriteTrip.getId(),
						"favoriteTrip-view");
				log.debug("Mein Favorit: " + favoriteTrip.getCreateTs() + " "
						+ favoriteTrip.getStartSite().getItemDesignation());

			}
			// in jeden Transfer meine Favoriten einbauen
			for (CrewChange cc : ccList) {
				for (Transfer transfer : cc.getTransfers()) {
					for (FavoriteTrip favoriteTrip : favList) {
						// nur den Start einbauen
						Transfer transferWithFirstSite = siteInRouteEingebauen(transfer, favoriteTrip.getStartSite());
						if (transferWithFirstSite != null) {
							// in den neuen Transfer die zweite Site einbauen
							Transfer transferWithFavTrips = siteInRouteEingebauen(transferWithFirstSite,
									favoriteTrip.getDestination());

							if (transferWithFavTrips != null) {
								resultList.add(getFreeCapacityForTrip(favoriteTrip, transferWithFavTrips, transfer));
							}
						}
					}
				}
			}

		}
		return resultList;

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
			if (onboard && capaMap.get(favSite.getId()) < minAvailable) {
				minAvailable = minAvailable - capaMap.get(favSite.getId());
			}
		}

		TripDTO tmp = new TripDTO();
		tmp.setBookedSeats(minAvailable);
		tmp.setSiteA(desiredTrip.getStartSite());
		tmp.setSiteB(desiredTrip.getDestination());
		tmp.setTransfer(transfer);

		return tmp;
	}

	public HashMap<UUID, Integer> getBookedSeatsMap(List<TripDTO> groupedTickets, Transfer transferWithFavTrips) {

		HashMap<UUID, Integer> resultCapaMap = new HashMap<UUID, Integer>();

		// über die tickets iterieren

		for (TripDTO ticketGroup : groupedTickets) {
			log.debug("Tickets " + ticketGroup.getSiteA().getItemDesignation() + " - " + ticketGroup.getSiteB().getItemDesignation() + ": " + ticketGroup.getBookedSeats());
			
			// capaMap enthält booked seats beim Verlassen der Site:
			// über die Strecke iterieren, wenn site = startSite -> PAX steigen
			// zu, wenn Site = destination -> PAX steigen aus
			boolean onboard = false;
			Standstill currentStandstill = transferWithFavTrips.getAnchorWaypoint();
			while(currentStandstill != null){
				Site site = currentStandstill.getSite();
				
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
				currentStandstill = currentStandstill.getNextWaypoint();
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

	private Transfer siteInRouteEingebauen(Transfer transfer, Site siteA) {
		// Ist Site schon drin? Dann einfach Kopie zurück
		if (transfer.getSiteHash().containsKey(siteA.getId())) {
			return transfer.getTransientCopy();
		}
		// List<Site> minSiteList = null;
		Transfer resultTransfer = null;
		CraftType craft = transfer.getCraftType();

		int minDistance = craft.getMaxRange();
		Standstill currentStandstill = transfer.getAnchorWaypoint();

		do {
			Waypoint nextOriginalWaypoint = currentStandstill.getNextWaypoint();
			//if (currentStandstill.getSite().getId().equals(siteA.getId())) {
				Waypoint addedWaypoint = createWaypoint(transfer, siteA, currentStandstill);
				Log.info(transfer.getRoute() + " Dist: " + transfer.getTotalDistance());
				if (transfer.getTotalDistance() < craft.getMaxRange()) {
					// siteResultList.add(site);
					if (transfer.getTotalDistance() < minDistance) {
						minDistance = transfer.getTotalDistance();
						resultTransfer = new Transfer(transfer.getSites());
						resultTransfer.setCraftType(craft);
					}

					//unlinkWaypoint(addedWaypoint);
				}
				unlinkWaypoint(addedWaypoint);
			//}
			currentStandstill = nextOriginalWaypoint;
		} while (currentStandstill != null);
		// }

		return resultTransfer;
	}

}