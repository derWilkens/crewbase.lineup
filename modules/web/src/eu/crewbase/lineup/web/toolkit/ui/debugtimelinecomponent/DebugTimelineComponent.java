package eu.crewbase.lineup.web.toolkit.ui.debugtimelinecomponent;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

import eu.crewbase.lineup.web.toolkit.ui.timelinecomponent.TimelineComponentState;

@JavaScript({"debugtimelinecomponent-connector.js", "vis.js" })
@StyleSheet({ "vis.css", "timeline.css" })
public class DebugTimelineComponent extends AbstractJavaScriptComponent {
    public DebugTimelineComponent() {
    }

	@Override
	protected TimelineComponentState getState() {
		return (TimelineComponentState) super.getState();
	}
}