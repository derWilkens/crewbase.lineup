package eu.crewbase.lineup.gui.xml.layout.loaders;

import eu.crewbase.lineup.gui.components.DebugTimeline;
import com.haulmont.cuba.gui.xml.layout.loaders.AbstractComponentLoader;

public class DebugTimelineLoader extends AbstractComponentLoader<DebugTimeline> {
    @Override
    public void createComponent() {
        resultComponent = factory.createComponent(DebugTimeline.class);
        loadId(resultComponent, element);
    }

    @Override
    public void loadComponent() {
    }
}
