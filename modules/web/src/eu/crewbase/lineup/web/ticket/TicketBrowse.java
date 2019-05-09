package eu.crewbase.lineup.web.ticket;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.wayfare.Ticket;

@UiController("lineup$Ticket.browse")
@UiDescriptor("ticket-browse.xml")
@LookupComponent("ticketsTable")
@LoadDataBeforeShow
public class TicketBrowse extends StandardLookup<Ticket> {
}