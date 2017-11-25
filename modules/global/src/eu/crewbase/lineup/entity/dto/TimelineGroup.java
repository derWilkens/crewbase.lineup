package eu.crewbase.lineup.entity.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;

import eu.crewbase.lineup.entity.AbstractNotPersistentStringIdEntity;
import eu.crewbase.lineup.entity.period.Period;

@MetaClass(name = "lineup$TimelineGroup")
public class TimelineGroup extends AbstractNotPersistentStringIdEntity {
	private static final long serialVersionUID = 3768626212104478342L;

	@MetaProperty
	protected String content;
	
	@MetaProperty
	protected String opsData; //wenn nach nicht sichtbaren Eigenschaften sortiert werden soll
	
	@MetaProperty
	protected String subgroupOrder;

	@MetaProperty
	protected String type;

	@MetaProperty
	protected String style;

	@MetaProperty
	protected String title;

	@MetaProperty
	protected Boolean visible;

	@MetaProperty
	private List<String> nestedGroups;

	@MetaProperty
	protected Boolean showNestedGroups;

	public TimelineGroup(String entityId, String content, String opsData) {
		this.id = entityId;
		this.content = content;
		this.opsData = opsData;
		visible = true;
	}

	@SuppressWarnings("unchecked")
	public TimelineGroup(Period entity, TimelineConfig timelineConfig) {
		this.showNestedGroups = false;
		this.id = ((Function<Period, String>) timelineConfig.getGroupIdFunction()).apply(entity);
		this.content = ((Function<Period, String>) timelineConfig.getParentGroupIdFunction()).apply(entity);
		this.opsData = this.content;
		// NestedGroups sind subGroups
		String nestedGroupId = ((Function<Period, String>) timelineConfig.getGroupLabelFunction()).apply(entity);
		if (nestedGroupId != null  && !this.nestedGroups.contains(nestedGroupId)) {
			this.addSubgroup(nestedGroupId);
			nestedGroups.add(nestedGroupId);
			this.showNestedGroups = true;
			this.subgroupOrder="function (c, d) {return c.content - d.content;}";
		}
		
	}

	public void addSubgroup(String group) {
		if (this.nestedGroups == null) {
			this.nestedGroups = new ArrayList<String>();
			this.showNestedGroups = true;
			//this.subgroupOrder="function (a, b) {return a.opsData - b.opsData;}";
		}
		if (group != null) {
			this.nestedGroups.add(group);
		}
	}

	public List<String> getNestedGroups() {
		return nestedGroups;
	}

	public void setNestedGroups(List<String> nestedGroups) {
		this.nestedGroups = nestedGroups;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public String getOpsData() {
		return opsData;
	}

	public void setOpsData(String opsData) {
		this.opsData = opsData;
	}
	
	public void setSubgroupOrder(String subgroupOrder) {
		this.subgroupOrder = subgroupOrder;
	}

	public String getSubgroupOrder() {
		return subgroupOrder;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setShowNestedGroups(Boolean showNestedGroups) {
		this.showNestedGroups = showNestedGroups;
	}

	public Boolean getShowNestedGroups() {
		return showNestedGroups;
	}
	
}