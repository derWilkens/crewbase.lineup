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
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Ticket;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

@Service(CrewChangeService.NAME)
public class CrewChangeServiceBean implements CrewChangeService {
	private static final Logger log = LoggerFactory.getLogger(CrewChangeServiceBean.class);

	@Inject
	public Persistence persistence;
	@Inject
	public DataManager dataManager;
	@Inject
	public Metadata metadata;
	@Inject
	public TravelOptionService travelOptionService;
	@Inject
	public TransferService transferService;

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
		 * 2 Standard-Waypoints anlegen A - B - A
		 */
		Waypoint awp1 = dataManager.create(Waypoint.class);
		awp1.setSite(dto.getDepartureSite());
		awp1.setTakeOff(dto.getStartDateTime());
		awp1.setTransfer(transfer);
		transfer.getWaypoints().add(awp1);

		Waypoint wp1 = dataManager.create(Waypoint.class);
		wp1.setSite(dto.getDestinationSite());
		wp1.setTransfer(transfer);
		transfer.getWaypoints().add(wp1);

		Waypoint wp2 = dataManager.create(Waypoint.class);
		wp2.setSite(dto.getDepartureSite());
		wp2.setTakeOff(dto.getStartDateTime());
		wp2.setTransfer(transfer);
		transfer.getWaypoints().add(wp2);

		cc.getTransfers().add(transfer);
		dataManager.commit(cc);
		
		if (dto.getFreeSeatsOutbound() != null) {
			transferService.createTickets(transfer.getId(), dto.getDepartureSite(), dto.getDepartureSite(),
					transfer.getCraftType().getSeats() - dto.getFreeSeatsOutbound());
		}
		if (dto.getFreeSeatsInbound() != null) {
			transferService.createTickets(transfer.getId(), dto.getDestinationSite(), dto.getDepartureSite(),
					transfer.getCraftType().getSeats() - dto.getFreeSeatsInbound());
		}
		travelOptionService.createTravelOptions(transfer.getId());
		
		return cc.getId();

	}

	private void createTicketsX(int freeSeats, Site siteA, Site siteB, Transfer transfer) {
		int amount = transfer.getCraftType().getSeats() - freeSeats;
		for (int i = 0; i < amount; i++) {
			Ticket ticket = metadata.create(Ticket.class);
			ticket.setTransfer(transfer);
			ticket.setStartSite(siteA);
			ticket.setDestinationSite(siteB);
			transfer.getTickets().add(ticket);
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
						Transfer transferWithFirstSite = null;// siteInRouteEinbauen(transfer,
																// favoriteTrip.getStartSite());
						if (transferWithFirstSite != null) {
							// in den neuen Transfer die zweite Site einbauen
							Transfer transferWithFavTrips = null;// siteInRouteEinbauen(transferWithFirstSite,favoriteTrip.getDestination());

							if (transferWithFavTrips != null) {
								// resultList.add(getFreeCapacityForTrip(favoriteTrip,
								// transferWithFavTrips, transfer));
							}
						}
					}
				}
			}

		}
		return resultList;

	}

}