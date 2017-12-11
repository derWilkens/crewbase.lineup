package eu.crewbase.lineup.gui.screens;

import java.util.Map;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import eu.crewbase.lineup.web.toolkit.ui.debugtimelinecomponent.DebugTimelineComponent;

public class Screen extends AbstractWindow {
	@Inject
	private ScrollBoxLayout timelineBox;
	private DebugTimelineComponent debugPlan;
	
	@Override
	public void init(Map<String, Object> params) {

		super.init(params);
		
	debugPlan = new DebugTimelineComponent();

	com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
	box.addComponent(debugPlan);
	}
}