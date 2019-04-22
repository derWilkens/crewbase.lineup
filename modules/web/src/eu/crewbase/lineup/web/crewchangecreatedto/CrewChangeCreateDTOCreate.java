package eu.crewbase.lineup.web.crewchangecreatedto;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("lineup$CrewChangeCreateDTO.create")
@UiDescriptor("crew-change-create-dto-create.xml")
@EditedEntityContainer("crewChangeCreateDTODc")
@LoadDataBeforeShow
public class CrewChangeCreateDTOCreate extends StandardEditor<CrewChangeCreateDTO> {

    @Inject
    private CollectionLoader<CraftType> craftTypesDl;
    @Inject
    private LookupField<CraftType> craftType;
    @Inject
    private LookupField freeSeatsInbound;
    @Inject
    private LookupField freeSeatsOutbound;
    @Inject
    private CollectionContainer<CraftType> craftTypesDc;

/*    private void initListener() {
        craftType.addValueChangeListener(e -> {
            List<Integer> list = new ArrayList<>();
            CraftType craftType = e.getValue();
            if (craftType != null) {
                for (int i = 0; i <= craftType.getSeats(); i++) {
                    list.add(i);
                }
                freeSeatsOutbound.setOptionsList(list);
                freeSeatsInbound.setOptionsList(list);
            }
        });
    }*/
    @Subscribe("craftType")
    protected void onValueChangeEvent(HasValue.ValueChangeEvent<CraftType> e) {
        List<Integer> list = new ArrayList<>();
        CraftType craftType = e.getValue();
        if (craftType != null) {
            for (int i = 0; i <= craftType.getSeats(); i++) {
                list.add(i);
            }
            freeSeatsOutbound.setOptionsList(list);
            freeSeatsInbound.setOptionsList(list);
        }
    }
    @Subscribe("modeOfTransferLF")
    private void onModeOfTransferLFValueChange(HasValue.ValueChangeEvent<String> event) {
        if (event.getValue() != null) {
            craftTypesDl.setParameter("modeOfTransfer.id",  event.getValue());
             //<![CDATA[select e from lineup$CraftType e where e.modeOfTransfer.id = :ds$modeOfTransferDs.id]]>
        } else {
            craftTypesDl.removeParameter("modeOfTransfer.id");
        }
        craftTypesDl.load();
    }
}