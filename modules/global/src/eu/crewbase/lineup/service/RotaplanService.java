package eu.crewbase.lineup.service;

import java.text.ParseException;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.PeriodDTO;
import eu.crewbase.lineup.entity.period.Period;

public interface RotaplanService {
    String NAME = "lineup_RotaplanService";
    Site getSiteByItemDesignation(String itemDesignation);
    Period createPeriod(PeriodDTO dto) throws ClassNotFoundException, ParseException;
    //Period readPeriod(PeriodDTO dto);
    Period updatePeriod(PeriodDTO dto);
    void deletePeriod(String entityId);
}