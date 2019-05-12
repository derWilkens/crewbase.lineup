package eu.crewbase.lineup.service;

import java.util.Date;
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
import eu.crewbase.lineup.entity.wayfare.Ticket;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

@Service(TransferService.NAME)
public class TransferServiceBean implements TransferService {

	private static final Logger log = LoggerFactory.getLogger(TransferServiceBean.class);

	@Inject
	public Persistence persistence;
	@Inject
	public DataManager dataManager;
	@Inject
	public Metadata metadata;
	@Inject
	public TravelOptionService travelOptionService;

	@Override
	public void createTickets(UUID travelOptionId) {
		try (Transaction tx = persistence.createTransaction()) {
			TravelOption travelOption = null;

			if (travelOptionId != null) {
				travelOption = persistence.getEntityManager().find(TravelOption.class, travelOptionId);
				createTickets(travelOption.getTransfer().getId(), travelOption.getFavoriteTrip().getStartSite(),
						travelOption.getFavoriteTrip().getDestination(), travelOption.getBookedSeats(), travelOptionId);
			}
			tx.commit();
		}
	}

	@Override
	public void createTickets(UUID transferId, Site siteA, Site siteB, int bookedSeats) {
		try (Transaction tx = persistence.createTransaction()) {
			createTickets(transferId, siteA, siteB, bookedSeats, null);
			tx.commit();
		}
		//nach dem Commit können die freien Kapazitäten neu berechnet werden 
		//@fixme: war eigentlich im onAfterUpdate des TransferListeners, funktioniert aber nicht
		travelOptionService.updateTravelOptions(transferId);
	}

	private void createTickets(UUID transferId, Site siteA, Site siteB, int bookedSeats, UUID travelOptionId) {

		Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);

		TravelOption travelOption = null;
		if (travelOptionId != null) {
			travelOption = persistence.getEntityManager().find(TravelOption.class, travelOptionId);
//			createTickets(travelOption.getTransfer().getId(), travelOption.getFavoriteTrip().getStartSite(),
//					travelOption.getFavoriteTrip().getDestination(), travelOption.getBookedSeats());
		}
		for (int i = 0; i < bookedSeats; i++) {
			Ticket ticket = metadata.create(Ticket.class);
			ticket.setTransfer(transfer);
			ticket.setStartSite(siteA);
			ticket.setDestinationSite(siteB);
			ticket.setTravelOption(travelOption);
			transfer.getTickets().add(ticket);
		}
		Waypoint wpA = metadata.create(Waypoint.class);
		Waypoint wpB = metadata.create(Waypoint.class);
		wpA.setSite(siteA);
		wpB.setSite(siteB);
		transfer.addWaypointShortestWay(wpA);
		transfer.addWaypointShortestWay(wpB);
		transfer.setUpdateTs(new Date());
		persistence.getEntityManager().persist(transfer);
		

	}

	@Override
	public boolean addWaypoint(UUID transferId, UUID siteId, int position) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			Site site = persistence.getEntityManager().find(Site.class, siteId);

			//wenn die Distanz zu groß ist --> false; könnte man mal in eine Exception umbauen
			boolean waypointAdded = addWaypoint(transfer, site, position);

			if(waypointAdded) {
				persistence.getEntityManager().persist(transfer);
				tx.commit();

				log.debug("Added Site:" + site.getItemDesignation());
				log.debug("New Route: " + transfer.getRouteShort());

				travelOptionService.updateTravelOptions(transferId);
			}

			return waypointAdded;
		}
	}

	@Override
	public boolean addWaypoint(UUID transferId, UUID siteId) {
		return addWaypoint(transferId,siteId,0);
	}

	private boolean addWaypoint(Transfer transfer, Site site, int position) {
		Waypoint waypoint = metadata.create(Waypoint.class);
		waypoint.setSite(site);
		waypoint.setTakeOff(transfer.getCrewChange().getStartDate());
		waypoint.setStopoverTime(15);
		if(position==0) {
			return transfer.addWaypointShortestWay(waypoint);
		}else
			return transfer.addWaypointAt(waypoint,position);

	}

	@Override
	public boolean moveWaypoint(UUID transferId, UUID siteId, int position) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			for (Waypoint waypoint : transfer.getWaypoints()) {
				if (waypoint.getSite().getId().equals(siteId)) {
					transfer.getWaypoints().remove(waypoint);
					transfer.getWaypoints().add(position, waypoint);
					//Collections.swap(list, i, j);
					transfer.renumber();
					break;
				}
			}
			persistence.getEntityManager().persist(transfer);
			tx.commit();
			travelOptionService.updateTravelOptions(transferId);
		}
		return false;
	}
	
	@Override
	public void removeWaypoint(UUID transferId, UUID siteId) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			for (Waypoint waypoint : transfer.getWaypoints()) {
				if (waypoint.getSite().getId().equals(siteId)) {
					persistence.getEntityManager().remove(waypoint);
					transfer.renumber();
					break;
				}
			}
			persistence.getEntityManager().persist(transfer);
			tx.commit();
			travelOptionService.updateTravelOptions(transferId);
		}

	}

	@Override
	public void removeWaypoint(UUID transferId, int position) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			persistence.getEntityManager().remove(transfer.getWaypoints().get(position));
			transfer.renumber();
			persistence.getEntityManager().persist(transfer);
			tx.commit();
		}

	}
}