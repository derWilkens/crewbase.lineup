package eu.crewbase.lineup.service;

import java.util.UUID;

import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

public interface CrewChangeService {
    String NAME = "lineup_CrewChangeService";
    UUID createCrewChange(CrewChangeCreateDTO dto);
    

//    public void moveWaypoint (UUID waypointId, UUID newPrevStandstill);
//    public void removeWaypoint (UUID waypointId);
    

}