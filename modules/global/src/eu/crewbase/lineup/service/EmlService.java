package eu.crewbase.lineup.service;

import eu.crewbase.lineup.entity.dto.TimelineDTO;

public interface EmlService {
    String NAME = "lineup_EmlService";
	TimelineDTO getEmlDto();
}