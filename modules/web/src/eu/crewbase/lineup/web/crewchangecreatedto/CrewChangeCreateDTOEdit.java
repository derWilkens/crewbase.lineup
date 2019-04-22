package eu.crewbase.lineup.web.crewchangecreatedto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.OptionsGroup;

import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.ModeOfTransfer;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.service.CrewChangeService;

public class CrewChangeCreateDTOEdit extends AbstractEditor<CrewChangeCreateDTO> {

	@Inject
	private CrewChangeService crewChangeService;

	@Inject
	protected LookupField craftType;
	@Inject
	protected LookupField freeSeatsOutbound;
	@Inject
	protected LookupField freeSeatsInbound;
	@Inject
	protected OptionsGroup modeOfTransfer;
	@Inject
	protected LookupField modeOfTransferLF;

	@Override
	public void init(Map<String, Object> params) {
		
		presetModeOfTransfer();
		initListener();
	}

	private void initListener() {
		craftType.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChanged(ValueChangeEvent e) {
				List<Integer> list = new ArrayList<>();
				CraftType craftType = (CraftType) e.getValue();
				if (craftType != null) {
					for (int i = 0; i <= craftType.getSeats(); i++) {
						list.add(i);
					}
					freeSeatsOutbound.setOptionsList(list);
					freeSeatsInbound.setOptionsList(list);
				}
			}
		});
	}

	private void presetModeOfTransfer() {
		modeOfTransfer.getOptionsDatasource().refresh();
		for(Object mofID:modeOfTransfer.getOptionsDatasource().getItemIds()){
			@SuppressWarnings("unchecked")
			ModeOfTransfer tempMot= (ModeOfTransfer)modeOfTransfer.getOptionsDatasource().getItem(mofID);
			if(tempMot.getMode().equals("Heli")){
				//modeOfTransfer.setValue(tempMot);
				modeOfTransferLF.setValue(tempMot);
			}
		}
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