package eu.crewbase.lineup.web.crewchangecreatedto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;

import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.service.CrewChangeService;

public class CrewChangeCreateDTOEdit extends AbstractEditor<CrewChangeCreateDTO> {

	@Inject
	private CrewChangeService crewChangeService;

	@Inject
	protected LookupField craftType;
	@Inject
	protected LookupField bookedSeatsOutbound;
	@Inject
	protected LookupField bookedSeatsInbound;

	@Override
	public void init(Map<String, Object> params) {
		
		craftType.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChanged(ValueChangeEvent e) {
				List<Integer> list = new ArrayList<>();
				CraftType craftType = (CraftType) e.getValue();
				if (craftType != null) {
					for (int i = 0; i <= craftType.getSeats(); i++) {
						list.add(i);
					}
					bookedSeatsOutbound.setOptionsList(list);
					bookedSeatsInbound.setOptionsList(list);
				}
			}
		});
	}

	@Override
	protected void initNewItem(CrewChangeCreateDTO item) {

	}

	@Override
	protected boolean preCommit() {
		//Daten an den Service übergeben
		crewChangeService.createCrewChange(this.getItem());
		return super.preCommit();
	}

}