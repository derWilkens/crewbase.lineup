package eu.crewbase.lineup.entity.dto;

import java.util.ArrayList;
import java.util.List;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

import eu.crewbase.lineup.entity.period.Period;

@MetaClass(name = "lineup$TimelineDTO")
public class TimelineDTO extends BaseUuidEntity {
	private static final long serialVersionUID = 3757688296152498888L;

	@MetaProperty
	protected List<TimelineGroup> groupList;

	@MetaProperty
	protected List<TimelineGroup> parentGroupList;

	@MetaProperty
	protected List<TimelineItem> timelineItemList;
	
	@MetaProperty
	protected List<DutyPeriodDTO> dutyPeriodTemplates;
	
	public void setParentGroupList(List<TimelineGroup> parentGroupList) {
		this.parentGroupList = parentGroupList;
	}

	public List<TimelineGroup> getParentGroupList() {
		return parentGroupList;
	}

	public List<TimelineGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TimelineGroup> groupList) {
		this.groupList = groupList;
	}

	public TimelineDTO() {
		this.timelineItemList = new ArrayList<TimelineItem>();
		this.groupList = new ArrayList<TimelineGroup>();
		this.parentGroupList = new ArrayList<TimelineGroup>();
		this.dutyPeriodTemplates = new ArrayList<DutyPeriodDTO>();
	}

	public void setTimelineItemList(List<TimelineItem> timelineItemList) {
		this.timelineItemList = timelineItemList;
	}

	public List<TimelineItem> getTimelineItemList() {
		return timelineItemList;
	}
	public List<DutyPeriodDTO> getPeriodTemplates() {
		return dutyPeriodTemplates;
	}

	public void setPeriodTemplates(List<DutyPeriodDTO> dutyPeriodTemplates) {
		this.dutyPeriodTemplates = dutyPeriodTemplates;
	}

	public void addItem(Period entity, TimelineConfig timelineConfig) {
		TimelineItem item = new TimelineItem(entity, timelineConfig);
		this.timelineItemList.add(item);
	}

	public void addItems(List<?> entities, TimelineConfig timelineConfig) {
		for (Object entity : entities) {
			addItem((Period) entity, timelineConfig);
		}
	}
}