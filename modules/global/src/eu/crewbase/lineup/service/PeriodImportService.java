package eu.crewbase.lineup.service;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.period.MaintenanceCampaign;

public interface PeriodImportService {
    String NAME = "lineup_PeriodImportService";
    int parseCsv(String rawPeriods);
	Site getSiteByItemDesignation(String itemDesignation);
	MaintenanceCampaign getCampaignByIdAndItemDesignation(String campaignNumber, String itemDesignation);
	int createOrUpdateCampaign();
	void clearStageTable();
    
}