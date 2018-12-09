package eu.crewbase.lineup.web.crewchange;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import eu.crewbase.lineup.entity.wayfare.CrewChange;

public class CrewChangeOverview extends AbstractLookup {
    @Named("crewChangesTable.create")
    private CreateAction crewChangesTableCreate;
    
    @Inject
    private CollectionDatasource<CrewChange, UUID> crewChangesDs;
    
    public void init(Map<String, Object> params) {
        crewChangesTableCreate.setWindowId("lineup$CrewChangeCreateDTO.edit");
        
    }
}