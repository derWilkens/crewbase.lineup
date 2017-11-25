package eu.crewbase.lineup.service;

import eu.crewbase.lineup.entity.coredata.Site;

public interface RotaplanService {
    String NAME = "lineup_RotaplanService";
    Site getSiteByItemDesignation(String itemDesignation);
}