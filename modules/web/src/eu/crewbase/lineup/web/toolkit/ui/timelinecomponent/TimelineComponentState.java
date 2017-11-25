package eu.crewbase.lineup.web.toolkit.ui.timelinecomponent;

import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

import eu.crewbase.lineup.entity.dto.DutyPeriodDTO;
import eu.crewbase.lineup.entity.dto.TimelineGroup;
import eu.crewbase.lineup.entity.dto.TimelineItem;


public class TimelineComponentState extends JavaScriptComponentState {
	
	private static final long serialVersionUID = -5285468521541170565L;
	
	public String start;
	public String end;
	public String format;
	public String dataAttributes;
	public List<TimelineGroup> timelineGroups; 
	public List<TimelineItem> timelineItems;
	public List<DutyPeriodDTO> dutyPeriodTemplates;
}
