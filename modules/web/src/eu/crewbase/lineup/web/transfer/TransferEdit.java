package eu.crewbase.lineup.web.transfer;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.LookupField;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;

public class TransferEdit extends AbstractEditor<Transfer> {
	
	@Inject
	private LookupField siteLookup;
	@Inject
	private DataManager dataManager;

    public void onAddSelectedSite(Component source) {
    	if(siteLookup.getValue()!=null){
    		Waypoint waypoint = dataManager.create(Waypoint.class);
    		Transfer transfer = this.getItem();
    		waypoint.setSite((Site) siteLookup.getValue());
    		this.getItem().addWaypointShortestWay(waypoint);
    		dataManager.commit(this.getItem());
    	}
    }
}