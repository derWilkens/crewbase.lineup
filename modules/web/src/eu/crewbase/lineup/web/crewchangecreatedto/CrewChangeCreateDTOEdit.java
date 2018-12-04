package eu.crewbase.lineup.web.crewchangecreatedto;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractEditor;

import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.service.CrewChangeService;

public class CrewChangeCreateDTOEdit extends AbstractEditor<CrewChangeCreateDTO> {

	@Inject
	private CrewChangeService crewChangeService;
	
    @Override
    protected void initNewItem(CrewChangeCreateDTO item) {
    	
    }
    
    @Override
    protected boolean preCommit() {
    	crewChangeService.createCrewChange(this.getItem());
    	//return false;
    	return super.preCommit();
    }
    
}