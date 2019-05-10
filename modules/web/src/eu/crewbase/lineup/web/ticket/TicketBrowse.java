package eu.crewbase.lineup.web.ticket;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.wayfare.Ticket;

@UiController("lineup$Ticket.browse")
@UiDescriptor("ticket-browse.xml")
@LookupComponent("ticketsTable")
@LoadDataBeforeShow
public class TicketBrowse extends StandardLookup<Ticket> {
    public Component generateAvailableCell(Entity entity) {
        int craftCapa = entity.getValue("seats");
        long bookedSeats = entity.getValue("total");
        return new Table.PlainTextCell(String.valueOf(craftCapa-bookedSeats));


    }
}