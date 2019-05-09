package eu.crewbase.lineup.web.transfer;

import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.wayfare.Transfer;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;

@UiController("lineup$Transfer.overview")
@UiDescriptor("transfer-overview.xml")
@LookupComponent("transfersTable")
@LoadDataBeforeShow
public class TransferOverview extends StandardLookup<Transfer> {
    @Inject
    private UiComponents uiComponents;

    public Component generateRoute(Transfer entity) {
        return new Table.PlainTextCell(entity.getRoute());
    }
    public Component generateBookedSeats(Transfer entity) {
        return new Table.PlainTextCell(Integer.toString(entity.getTickets().size()));
    }
    public Component generateFreeSeats(Transfer entity) {
        return new Table.PlainTextCell(Integer.toString(entity.getCraftType().getSeats()*2- entity.getTickets().size()*1));
    }
}