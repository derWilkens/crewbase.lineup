package eu.crewbase.lineup.service;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.period.MaintenanceCampaign;

public interface PeriodImportService {
    String NAME = "lineup_PeriodImportService";
    public int parseCsv(String rawPeriods);
	public Site getSiteByItemDesignation(String itemDesignation);
	public MaintenanceCampaign getCampaignByIdAndItemDesignation(String campaignNumber, String itemDesignation);
	public int createOrUpdateCampaign();
	public void clearStageTable();
    
}