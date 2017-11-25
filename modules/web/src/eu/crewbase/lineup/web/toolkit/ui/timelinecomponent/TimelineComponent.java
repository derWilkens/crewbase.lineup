package eu.crewbase.lineup.web.toolkit.ui.timelinecomponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

import elemental.json.JsonArray;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.dto.TimelineGroup;
import eu.crewbase.lineup.entity.dto.TimelineItem;


@JavaScript({"timelinecomponent-connector.js", "vis.js"})
@StyleSheet({"vis.css"})
public class TimelineComponent extends AbstractJavaScriptComponent {
	   
	private static final long serialVersionUID = -1963421147896570853L;
	
	private HashMap<UserPreferencesContext, TimelineDTO> dtoList;
	
    public interface ValueChangeListener {
        void valueChanged(String[] newData);
    }

    private ValueChangeListener listener;
    
	
	public TimelineComponent() {
		getState().timelineItems = new ArrayList<TimelineItem>();
		getState().timelineGroups = new ArrayList<TimelineGroup>();
		dtoList = new HashMap<UserPreferencesContext, TimelineDTO>();
		addFunction("valueChanged", arguments -> {
            JsonArray array = arguments.getArray(0);
            String[] values = new String[1];
            values[0] = array.getString(0);
          
            listener.valueChanged(values);
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
	
	public void addDTO(UserPreferencesContext context, TimelineDTO dto){
		dtoList.put(context, dto);
	}
	public void refresh(){
		getState().timelineItems.clear();// = new ArrayList<TimelineItem>();
		getState().timelineGroups.clear(); //= new ArrayList<TimelineGroup>();
		
		for (TimelineDTO timelineDTO : dtoList.values()) {
			getState().timelineItems.addAll(timelineDTO.getTimelineItemList());
			getState().timelineGroups.addAll(timelineDTO.getGroupList());
			//getState().timelineGroups.addAll(timelineDTO.getParentGroupList());
		}
		
	}
	
	
	
	
}