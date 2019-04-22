package eu.crewbase.lineup.web.transfer;

import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.wayfare.Transfer;

import javax.inject.Inject;

@UiController("lineup$Transfer.overview")
@UiDescriptor("transfer-overview.xml")
@LookupComponent("transfersTable")
@LoadDataBeforeShow
public class TransferOverview extends StandardLookup<Transfer> {
    @Inject
    private UiComponents uiComponents;

    public Component generateTakeOffCell(Transfer entity) {
        //Table.PlainTextCell cell = uiComponents.create(Table.PlainTextCell.class);
        return new Table.PlainTextCell(entity.getRoute());
    }
}