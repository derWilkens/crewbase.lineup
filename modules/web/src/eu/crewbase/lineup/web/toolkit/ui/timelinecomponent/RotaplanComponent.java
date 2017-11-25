package eu.crewbase.lineup.web.toolkit.ui.timelinecomponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

import elemental.json.JsonObject;
import eu.crewbase.lineup.entity.dto.DutyPeriodDTO;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.dto.TimelineGroup;
import eu.crewbase.lineup.entity.dto.TimelineItem;
import eu.crewbase.lineup.entity.period.Period;


@JavaScript({"rotaplan-connector.js", "vis.js"})
@StyleSheet({"vis.css", "timeline.css"})
public class RotaplanComponent extends AbstractJavaScriptComponent {
	   
	private static final long serialVersionUID = -1963421147896570853L;
	
	private HashMap<String, TimelineDTO> dtoList;
	
    public interface RotaplandChangeListener {
		void itemAdded(JsonObject jsonItem);
		void itemMoved(JsonObject jsonItem);
		void itemDeleted(JsonObject jsonItem);
		void editItem(String id);
		void addSubItem(JsonObject jsonItem);
    }

    private RotaplandChangeListener listener;
	
	public RotaplanComponent() {
		getState().timelineItems = new ArrayList<TimelineItem>();
		getState().timelineGroups = new ArrayList<TimelineGroup>();
		getState().dutyPeriodTemplates = new ArrayList<DutyPeriodDTO>();
		
		dtoList = new HashMap<String,TimelineDTO>();
		
		addFunction("itemAdded", arguments -> {
            listener.itemAdded(arguments.getObject(0));
        });
		addFunction("itemMoved", arguments -> {
            listener.itemMoved(arguments.getObject(0));
        });
		addFunction("itemDeleted", arguments -> {
            listener.itemDeleted(arguments.getObject(0));
        });
		addFunction("editItem", arguments -> {
            listener.editItem(arguments.getString(0));
        });
	}

    @Override
    protected TimelineComponentState getState() {
        return (TimelineComponentState) super.getState();
    }
    
	public String getStart() {
		return getState().start;
	}
	public void setStart(String start) {
		getState().start = start;
	}
	public String getEnd() {
		return getState().end;
	}
	public void setEnd(String end) {
		getState().end = end;
	}
	public String getFormat() {
		return getState().format;
	}
	public void setFormat(String format) {
		getState().format = format;
	}
	public String getDataAttributes() {
		return getState().dataAttributes;
	}
	public void setDataAttributes(String dataAttributes) {
		getState().dataAttributes = dataAttributes;
	}
	public Collection<TimelineGroup> getTimelineGroups() {
		return getState().timelineGroups;
	}
	public void setTimelineGroups(List<TimelineGroup> timelineGroups) {
		getState().timelineGroups = timelineGroups;
	}
	public Collection<TimelineItem> getTimelineItems() {
		return getState().timelineItems;
	}
	public void setTimelineItems(List<TimelineItem> timelineItems) {
		getState().timelineItems = timelineItems;
	}
	public Collection<DutyPeriodDTO> getDutyPeriodTemplate() {
		return getState().dutyPeriodTemplates;
	}
	public void setDutyPeriodTemplate(List<DutyPeriodDTO> dutyPeriodTemplates) {
		getState().dutyPeriodTemplates = dutyPeriodTemplates;
	}	
    public RotaplandChangeListener getListener() {
        return listener;
    }
    public void setListener(RotaplandChangeListener listener) {
        this.listener = listener;
    }

	public void addDTO(String key, TimelineDTO dto) {
		getState().timelineItems.clear();
		getState().timelineGroups.clear(); 
		getState().dutyPeriodTemplates.clear();

		
		dtoList.put(key, dto);
		for (TimelineDTO timelineDTO : dtoList.values()) {
			getState().timelineItems.addAll(timelineDTO.getTimelineItemList());
			getState().timelineGroups.addAll(timelineDTO.getGroupList());
			getState().dutyPeriodTemplates.addAll(timelineDTO.getDutyPeriodTemplates());
		}
	}

	public void addTimelineItem(TimelineItem timelineItem) {
		getState().timelineItems.removeIf(i -> i.getId().equals(timelineItem.getId().toString()));
		getState().timelineItems.add(timelineItem);
	}

	public void removeItem(Period period) {
		boolean removed = getState().timelineItems.removeIf(i -> i.getId().equals(period.getId().toString()));
		if(!removed){
		TimelineItem removeItem = null;
		for(TimelineItem item:getState().timelineItems){
			if(item.getId().equals(period.getId().toString())){
				removeItem = item;
			}
		}
		}
		
	}
}