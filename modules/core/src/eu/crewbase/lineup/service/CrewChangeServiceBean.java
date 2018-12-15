package eu.crewbase.lineup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.wayfare.AnchorWaypoint;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.Standstill;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

@Service(CrewChangeService.NAME)
public class CrewChangeServiceBean implements CrewChangeService {
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

			persistence.getEntityManager().persist(cc);
			tx.commit();
		}
		return cc.getId();
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
			CraftType craft = transfer.getCraftType();
			List<Site> siteList = persistence.getEntityManager().createQuery("select s from lineup$Site s", Site.class)
					.getResultList();
			craft.getMetaClass();
			// welche Sites können noch erreicht werden?
			// - Liste der vorhandenen Sites übergeben
			transfer.getNextWaypoint().getSite();

			// jede site an jeder position einfügen und gesamtdistanz ausrechnen
			Standstill currentStandstill = transfer.getNextWaypoint().getPreviousStandstill();
			for (Site site : siteList) {

				do {
					Waypoint addWaypoint = createWaypoint(transfer, site, currentStandstill);
					currentStandstill.getNextWaypoint();
					int totalDistance = transfer.getTotalDistance();
					if (totalDistance < craft.getMaxRange()) {
						siteResultList.add(site);
					}
					removeWaypoint(addWaypoint.getId());
				} while (currentStandstill != null);

			}

			// - für jede enthaltene Site die weiteren Sites im Umkreis < x km
			// finden
			// evtl. einfach Matrix aufstellen von x nach y für alle 15
			// Helidecks

			List<Site> transferSites = new ArrayList<Site>();
			// transfer.getWaypointList().stream().collect(Collectors.toList(Waypoint::getSite));
			// for (Waypoint waypoint : transfer.getWaypointList()) {
			// transferSites.add(waypoint.getSite());
			// }

			int length = getMinLength(transferSites);

			// jeweils eine noch nicht vorhandene hinzufügen und beste Route
			// berechnen, ist die beste Route kürzer als Range?
			// dann hinzufügen mit delta in km um zus. Kosten darzustellen
			// wie lang darf der Umweg für die PAX sein? Wenn keiner drin sitzt?
			// nur hinzufügen, wenn zus. Strecke kürzer als Weg Hin/Rück allein

		}
		return null;
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