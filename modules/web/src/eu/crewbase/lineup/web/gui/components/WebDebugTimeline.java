package eu.crewbase.lineup.web.gui.components;

import com.haulmont.cuba.web.gui.components.WebAbstractComponent;

import eu.crewbase.lineup.gui.components.DebugTimeline;

public class WebDebugTimeline extends WebAbstractComponent<eu.crewbase.lineup.web.toolkit.ui.debugtimelinecomponent.DebugTimelineComponent> implements DebugTimeline {
    public WebDebugTimeline() {
        this.component = new eu.crewbase.lineup.web.toolkit.ui.debugtimelinecomponent.DebugTimelineComponent();
    }
}