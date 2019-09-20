package eu.crewbase.lineup.web.crewchangecreatedto;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.ModeOfTransfer;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.service.CrewChangeService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("lineup$CrewChangeCreateDTO.create")
@UiDescriptor("crew-change-create-dto-create.xml")
@EditedEntityContainer("crewChangeCreateDTODc")
@LoadDataBeforeShow
public class CrewChangeCreateDTOCreate extends StandardEditor<CrewChangeCreateDTO> {

    @Inject
    private CrewChangeService crewChangeService;
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

    @Subscribe("onInitEntity")
    protected void onInitEntity(InitEntityEvent<CrewChangeCreateDTO> event) {
        if(event.getEntity()==null){
            this.setEntityToEdit(new CrewChangeCreateDTO());

        }

    }

    @Subscribe("startDateTimeField")
    protected void onValueChangeEventX(HasValue.ValueChangeEvent e) {
        System.out.println(e);
    }

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
    private void onModeOfTransferLFValueChange(HasValue.ValueChangeEvent<ModeOfTransfer> event) {
        if (event.getValue() != null) {
            ModeOfTransfer mode = event.getValue();
            craftTypesDl.setParameter("modeOfTransferId", mode.getId());
        } else {
            craftTypesDl.removeParameter("modeOfTransferId");
        }
        craftTypesDl.load();
    }

    // controller
    @Inject
    private Notifications notifications;

    @Subscribe("createCrewChange")
    protected void oncreateCrewChangeActionPerformed(Action.ActionPerformedEvent event) {
        //Notifications.NotificationBuilder notificationBuilder = notifications.create(Notifications.NotificationType.HUMANIZED);
        //notificationBuilder.withCaption(this.getEditedEntity().getStartDateTime().toString()).show();
        //crewChangeService.createCrewChange(this.getEditedEntity());
    }

    @Subscribe
    protected void onBeforeClose(BeforeCloseEvent event) {

        if (event.getCloseAction().toString().equals("CloseAction{actionId='commit'}")) {
            crewChangeService.createCrewChange(this.getEditedEntity());
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption("Flight successfull created.").show();
        }
    }
}