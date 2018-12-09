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
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Way;
import eu.crewbase.lineup.entity.wayfare.Waypoint;


@Service(CrewChangeService.NAME)
public class CrewChangeServiceBean implements CrewChangeService {
	@Inject
	private Persistence persistence;

	@Inject
	private Metadata metadata;
	
	@Override
	public int createCrewChange(CrewChangeCreateDTO dto) {
		// TODO Auto-generated method stub
		try (Transaction tx = persistence.createTransaction()) {

			/**
			 * Crew Change 
			 */
			CrewChange cc = (CrewChange) metadata.create(CrewChange.class);
			cc.setFlightDate(dto.getCrewChangeDate());
			
			/**
			 * Transfer anlegen und verknüpfen
			 */
			Transfer transfer = metadata.create(Transfer.class);
			transfer.setTransferOrderNo(1);
			transfer.setCrewChange(cc);
			

			/**
			 * 3 Standard-Waypoints anlegen A - B - A
			 * die Waypoints dem Transfer in der beliebiger Reihenfolge hinzufügen 
			 */
			Waypoint wp1 = metadata.create(Waypoint.class);
			Waypoint wp2 = metadata.create(Waypoint.class);
			Waypoint wp3 = metadata.create(Waypoint.class);
			wp1.setSite(dto.getDepartureSite());
			wp2.setSite(dto.getArrivalSite());
			wp3.setSite(dto.getDepartureSite());
			wp1.setOrderNo(1);
			wp2.setOrderNo(2);
			wp3.setOrderNo(3);
			wp1.setTakeOff(dto.getTakeOff());
			wp2.setTakeOff(dto.getTakeOff());
			wp3.setTakeOff(dto.getTakeOff());
			
			
			/**
			 * 2 Wege/Kanten erzeugen zwischen A-B und B-A
			 * Der Start des Graphen definiert sich dadurch, 
			 * dass es nur eine Kante hat  
			 */
			Way way1 = metadata.create(Way.class);
			Way way2 = metadata.create(Way.class);
			way1.setDepartureSite(wp1);
			way1.setArrivalSite(wp2);
			way1.setOccupiedSeats(dto.getOccupiedSeatsWay1());
			way2.setDepartureSite(wp2);
			way2.setDepartureSite(wp3);
			way2.setOccupiedSeats(dto.getOccupiedSeatsWay2());
			
			//Die Wegpunkte der Reise hinzufügen (alle Knoten in beliebiger Reihenfolge)
			transfer.getWaypointList().add(wp1);
			transfer.getWaypointList().add(wp2);
			transfer.getWaypointList().add(wp3);
			
			//Die Kanten zur Reise hinzufügen (Die Reihenfolge der Kanten ist theoretisch beliebig
			
			transfer.getWays().add(way1);
			transfer.getWays().add(way2);
			
			cc.getTransfers().add(transfer);
			
			persistence.getEntityManager().persist(cc);
			tx.commit();
		}
		return 1;

	}

	@Override
	public void addWaypoint(UUID transferId, UUID siteId, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveWaypoint(UUID waypointId, int newPosition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeWaypoint(UUID waypointId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Site> getReachableSites(UUID transferId) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);
			CraftType craft = transfer.getCraftType();
			craft.getMetaClass();
			//welche Sites können noch erreicht werden?
			//- Liste der vorhandenen Sites übergeben
			//
			
			List<Site> transferSites = new ArrayList<Site>(); 
			//transfer.getWaypointList().stream().collect(Collectors.toList(Waypoint::getSite));
			for (Waypoint waypoint : transfer.getWaypointList()) {
				transferSites.add(waypoint.getSite());
			}
			
			int length = getMinLength(transferSites);
			
			
			
			//jeweils eine noch nicht vorhandene hinzufügen und beste Route berechnen, ist die beste Route kürzer als Range?
			//dann hinzufügen mit delta in km um zus. Kosten darzustellen
			//wie lang darf der Umweg für die PAX sein? Wenn keiner drin sitzt? 
			//nur hinzufügen, wenn zus. Strecke kürzer als Weg Hin/Rück allein
			
			

		}
		return null;
	}

	private int getMinLength(List<Site> transferSites) {
		
		return 0;
	}
	
}