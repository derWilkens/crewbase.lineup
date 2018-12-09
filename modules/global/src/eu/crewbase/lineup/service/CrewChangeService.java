package eu.crewbase.lineup.service;

import java.util.List;
import java.util.UUID;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;

public interface CrewChangeService {
    String NAME = "lineup_CrewChangeService";
    public int createCrewChange(CrewChangeCreateDTO dto);
    
    public void addWaypoint (UUID transferId, UUID siteId, int position);
    public void moveWaypoint (UUID waypointId, int newPosition);
    public void removeWaypoint (UUID waypointId);
    
    public List<Site> getReachableSites(UUID transferId);
    
}