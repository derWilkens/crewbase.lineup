package eu.crewbase.lineup.entity.wayfare;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import eu.crewbase.lineup.entity.coredata.AppUser;

import javax.persistence.*;

@NamePattern("  %s|passenger")
@Table(name = "LINEUP_TICKET")
@Entity(name = "lineup$Ticket")
public class Ticket extends StandardEntity {
    private static final long serialVersionUID = -5485980273234160078L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_ID")
    protected Transfer transfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PASSENGER_ID")
    protected AppUser passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_OPTION_ID")
    protected TravelOption travelOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "START_WAYPOINT_ID")
    protected Waypoint startWaypoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESTINATION_WAYPOINT_ID")
    protected Waypoint destinationWaypoint;

    public Waypoint getDestinationWaypoint() {
        return destinationWaypoint;
    }

    public void setDestinationWaypoint(Waypoint destinationWaypoint) {
        this.destinationWaypoint = destinationWaypoint;
    }

    public Waypoint getStartWaypoint() {
        return startWaypoint;
    }

    public void setStartWaypoint(Waypoint startWaypoint) {
        this.startWaypoint = startWaypoint;
    }

    public void setTravelOption(TravelOption travelOption) {
        this.travelOption = travelOption;
    }

    public TravelOption getTravelOption() {
        return travelOption;
    }


    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setPassenger(AppUser passenger) {
        this.passenger = passenger;
    }

    public AppUser getPassenger() {
        return passenger;
    }


}