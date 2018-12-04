package eu.crewbase.lineup.service;

import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;

public interface CrewChangeService {
    String NAME = "lineup_CrewChangeService";
    public int createCrewChange(CrewChangeCreateDTO dto);
}