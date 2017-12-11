package eu.crewbase.lineup.service;

import java.util.Collection;
import java.util.Date;

import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.dto.TimelineItem;
import eu.crewbase.lineup.entity.period.OperationPeriod;
import eu.crewbase.lineup.entity.period.Period;

public interface TimelineService {
    String NAME = "lineup_TimelineService";
    TimelineDTO getDto(UserPreferencesContext context);
    TimelineDTO getRotoplanDto();
    Collection<AppUser> getPersonsOnDuty();
    TimelineItem periodToTimelineItem(Period period, UserPreferencesContext context);
	OperationPeriod getOperationPeriod(Site site, Date start, Date end);
}