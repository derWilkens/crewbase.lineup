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
		Transfer transfer = dataManager.create(Transfer.class);
		Waypoint wp1;
		Waypoint wp2;
		Waypoint wp3;

		try (Transaction tx = persistence.getTransaction()) {
			cc = dataManager.create(CrewChange.class);
			cc.setStartDate(dto.getStartDateTime());

			persistence.getEntityManager().persist(cc);

			/**
			 * Transfer anlegen und verknüpfen
			 */
			transfer.setTransferOrderNo(1);
			transfer.setCrewChange(cc);
			transfer.setCraftType(dto.getCraftType());


			cc.getTransfers().add(transfer);
			//persistence.getEntityManager().persist(transfer);

			/**
			 * 2 Standard-Waypoints anlegen A - B - A
			 */

			wp1 = addWaypoint(transfer, dto.getDepartureSite());
			wp2 = addWaypoint(transfer, dto.getDestinationSite());
			wp3 = addWaypoint(transfer, dto.getDepartureSite());

			persistence.getEntityManager().persist(transfer);
			tx.commit();
		}

		if (dto.getFreeSeatsOutbound() != null) {
			transferService.createTickets(transfer.getId(), wp1,wp2,
					transfer.getCraftType().getSeats() - dto.getFreeSeatsOutbound());
		}
		if (dto.getFreeSeatsInbound() != null) {
			transferService.createTickets(transfer.getId(), wp2,wp3,
					transfer.getCraftType().getSeats() - dto.getFreeSeatsInbound());
		}
		// @fixme: das darf gerne zum TransferListener, auch das funktioniert
		// irgendwie nicht
		// travelOptionService.createTravelOptions(transfer.getId());

		return cc.getId();

	}

	private Waypoint addWaypoint(Transfer transfer, Site site) {
		Waypoint wp = dataManager.create(Waypoint.class);
		wp.setSite(site);
		wp.setTakeOff(transfer.getCrewChange().getStartDate());
		wp.setStopoverTime(15);
		transfer.addWaypoint(wp);
		return wp;
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