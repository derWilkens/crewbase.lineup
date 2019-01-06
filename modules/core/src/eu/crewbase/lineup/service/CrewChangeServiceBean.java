package eu.crewbase.lineup.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;

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
	@Inject
	private TravelOptionService tos;

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
		transfer.setCraftType(dto.getCraftType());

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
		wp1.setSite(dto.getDestinationSite());
		wp1.setTransfer(transfer);
		// wp1.setNextWaypoint(awp1); geht nicht, weil der letzte Punkt
		// grundsätzlich der erste ist - das ist aber ein Problem

		awp1.setNextWaypoint(wp1);
		awp1.setStartDateTime(dto.getStartDateTime());
		wp1.setPreviousStandstill(awp1);
		cc.getTransfers().add(transfer);
		if (dto.getFreeSeatsOutbound() != null) {
			createTickets(dto.getFreeSeatsOutbound(), dto.getDepartureSite(), dto.getDepartureSite(), transfer);
		}
		if (dto.getFreeSeatsInbound() != null) {
			createTickets(dto.getFreeSeatsInbound(), dto.getDestinationSite(), dto.getDepartureSite(), transfer);
		}
		dataManager.commit(cc);
		Transfer one = dataManager.load(Transfer.class).id(transfer.getId()).one();

		tos.createTravelOption(one);
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
			transfer.setCraftType(dto.getCraftType());

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
			wp1.setSite(dto.getDestinationSite());
			wp1.setTransfer(transfer);
			// wp1.setNextWaypoint(awp1); geht nicht, weil der letzte Punkt
			// grundsätzlich der erste ist - das ist aber ein Problem

			awp1.setNextWaypoint(wp1);
			awp1.setStartDateTime(dto.getStartDateTime());
			wp1.setPreviousStandstill(awp1);
			cc.getTransfers().add(transfer);
			if (dto.getFreeSeatsOutbound() != null) {
				createTickets(dto.getFreeSeatsOutbound(), dto.getDepartureSite(), dto.getDepartureSite(), transfer);
			}
			if (dto.getFreeSeatsInbound() != null) {
				createTickets(dto.getFreeSeatsInbound(), dto.getDestinationSite(), dto.getDepartureSite(), transfer);
			}

			persistence.getEntityManager().persist(cc);
			tx.commit();
		}
		return cc.getId();
	}

	private void createTickets(int freeSeats, Site siteA, Site siteB, Transfer transfer) {
		int amount = transfer.getCraftType().getSeats() - freeSeats;
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
		waypoint.linkWaypoint(prevStandstill);
		log.debug(
				"Waypoint " + site.getSiteName() + " hinter " + prevStandstill.getSite().getSiteName() + " eingefügt.");
		return waypoint;
	}



	@Override
	public void moveWaypoint(UUID waypointId, UUID newPrevStandstill) {
		try (Transaction tx = persistence.createTransaction()) {
			Waypoint waypoint = persistence.getEntityManager().find(Waypoint.class, waypointId);
			Standstill prevStandstill = persistence.getEntityManager().find(Standstill.class, newPrevStandstill);
			waypoint.unlink();
			waypoint.linkWaypoint(prevStandstill);
			tx.commit();
		}
	}

	@Override
	public void removeWaypoint(UUID waypointId) {
		try (Transaction tx = persistence.createTransaction()) {
			Waypoint waypoint = persistence.getEntityManager().find(Waypoint.class, waypointId);
			waypoint.unlink();
			persistence.getEntityManager().remove(waypoint);
			tx.commit();
		}
	}

	/**
	 * Liefert zukünftige mögliche Mitfluggelegenheiten auf Basis meiner
	 * Favoriten
	 */
	public List<TripDTO> getMyTripsX(Date dateRangeStart, Date dateRangeEnd) {
		List<TripDTO> resultList = new ArrayList<TripDTO>();

		// Zukünftige CrewChanges und meine Favoriten holen
		try (Transaction tx = persistence.createTransaction()) {

			if (dateRangeStart == null) {
				dateRangeStart = new Date();
			}
			if (dateRangeEnd == null) {
				// dateRangeEnd = Calendar.getInstance()
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
						Transfer transferWithFirstSite = null;//siteInRouteEinbauen(transfer, favoriteTrip.getStartSite());
						if (transferWithFirstSite != null) {
							// in den neuen Transfer die zweite Site einbauen
							Transfer transferWithFavTrips = null;//siteInRouteEinbauen(transferWithFirstSite,favoriteTrip.getDestination());

							if (transferWithFavTrips != null) {
								//resultList.add(getFreeCapacityForTrip(favoriteTrip, transferWithFavTrips, transfer));
							}
						}
					}
				}
			}

		}
		return resultList;

	}







	






}