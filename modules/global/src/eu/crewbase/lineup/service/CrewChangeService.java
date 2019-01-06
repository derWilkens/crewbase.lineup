package eu.crewbase.lineup.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.dto.TripDTO;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

public interface CrewChangeService {
    String NAME = "lineup_CrewChangeService";
    public UUID createCrewChange(CrewChangeCreateDTO dto);
    
    public Waypoint addWaypoint (UUID transferId, UUID siteId, UUID prevStandstill);
    public void moveWaypoint (UUID waypointId, UUID newPrevStandstill);
    public void removeWaypoint (UUID waypointId);
    
    public List<Site> getReachableSites(UUID transferId);
    public List<TripDTO> getMyTrips(Date dateRangeStart, Date dateRangeEnd);
    
    public List<TripDTO> getGroupedTickets(Transfer transfer);
    public HashMap<UUID, Integer> getBookedSeatsMap(List<TripDTO> groupedTickets, Transfer transferWithFavTrips); 
    public TripDTO getFreeCapacityForTrip(FavoriteTrip desiredTrip,  Transfer transferWithFavTrips, Transfer transfer);
    
    public void createTravelOption(Transfer transfer);
    public void sendTravelOptions();

}