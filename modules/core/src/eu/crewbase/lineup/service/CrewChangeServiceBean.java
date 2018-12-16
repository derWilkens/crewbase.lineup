package eu.crewbase.lineup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.google.common.base.Ticker;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.controllers.FileDownloadController;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.wayfare.AnchorWaypoint;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
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
	private Metadata metadata;

	@Override
	public UUID createCrewChange(CrewChangeCreateDTO dto) {

		CrewChange cc = null;
		try (Transaction tx = persistence.createTransaction()) {

			/**
			 * Crew Change
			 */
			cc = (CrewChange) metadata.create(CrewChange.class);
			cc.setFlightDate(dto.getStartDateTime());

			/**
			 * Transfer anlegen und verknüpfen
			 */
			Transfer transfer = metadata.create(Transfer.class);
			transfer.setTransferOrderNo(1);
			transfer.setCrewChange(cc);

			/**
			 * 2 Standard-Waypoints anlegen A - B - (A) Endpunkt ist implizit
			 */
			AnchorWaypoint awp1 = metadata.create(AnchorWaypoint.class);
			awp1.setSite(dto.getDepartureSite());
			awp1.setStartDateTime(dto.getStartDateTime());
			transfer.setAnchorWaypoint(awp1);
			// transfer.setNextWaypoint(awp1);

			Waypoint wp1 = metadata.create(Waypoint.class);
			wp1.setSite(dto.getArrivalSite());
			// wp1.setNextWaypoint(awp1); geht nicht, weil der letzte Punkt
			// grundsätzlich der erste ist - das ist aber ein Problem

			awp1.setNextWaypoint(wp1);
			awp1.setStartDateTime(dto.getStartDateTime());
			wp1.setPreviousStandstill(awp1);
			cc.getTransfers().add(transfer);
			
			createTickets(dto.getOccupiedSeatsWay1(), dto, transfer);
			createTickets(dto.getOccupiedSeatsWay2(), dto, transfer);

			persistence.getEntityManager().persist(cc);
			tx.commit();
		}
		return cc.getId();
	}

	private void createTickets(int amount, CrewChangeCreateDTO dto, Transfer transfer) {
		for(int i = 0; i < amount;i++){
			Ticket ticket = metadata.create(Ticket.class);
			ticket.setTransfer(transfer);
			ticket.setStartSite(dto.getDepartureSite());
			ticket.setDestinationSite(dto.getArrivalSite());
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
			//List<Waypoint> entSites = persistence.getEntityManager().createQuery("select w from lineup$Waypoint w where w.transfer.Id = '" + transfer.getId()+"'", Waypoint.class).getResultList();
			CraftType craft = transfer.getCraftType();
			List<Site> siteList = persistence.getEntityManager().createQuery("select s from lineup$Site s", Site.class)
					.getResultList();
			craft = metadata.create(CraftType.class);
			craft.setMaxRange(10000);
			
			// welche Sites können noch erreicht werden?
			// - Liste der vorhandenen Sites übergeben

			// jede site an jeder position einfügen und gesamtdistanz ausrechnen
			
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

	private int getMinLength(List<Site> transferSites) {

		return 0;
	}

	private double getDistance(List<Waypoint> waypointList) {
		double softScore = 0;
		for (Waypoint waypoint : waypointList) {
			Standstill previousStandstill = waypoint.getPreviousStandstill();
			if (previousStandstill != null) {
				Transfer transfer = waypoint.getTransfer();

				// vehicleDemandMap.put(transfer, vehicleDemandMap.get(transfer)
				// + waypoint.getDemand());

				// Score constraint distanceToPreviousStandstill
				softScore = waypoint.getDistanceFromPreviousStandstill();
				if (waypoint.getNextWaypoint() == null) {
					// Score constraint distanceFromLastCustomerToDepot
					softScore -= waypoint.getSite().getDistanceTo(transfer.getSite());
					// softScore -=
					// customer.getLocation().getDistanceTo(vehicle.getLocation());

				}
			}
		}
		return softScore;
	}

}