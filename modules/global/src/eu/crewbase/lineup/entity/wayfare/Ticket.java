package eu.crewbase.lineup.entity.wayfare;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Site;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s %s|startSite,destinationSite,passenger")
@Table(name = "LINEUP_TICKET")
@Entity(name = "lineup$Ticket")
public class Ticket extends StandardEntity {
    private static final long serialVersionUID = -5485980273234160078L;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRANSFER_ID")
    protected Transfer transfer;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "START_SITE_ID")
    protected Site startSite;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DESTINATION_SITE_ID")
    protected Site destinationSite;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PASSENGER_ID")
    protected AppUser passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_OPTION_ID")
    protected TravelOption travelOption;

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

    public void setStartSite(Site startSite) {
        this.startSite = startSite;
    }

    public Site getStartSite() {
        return startSite;
    }

    public void setDestinationSite(Site destinationSite) {
        this.destinationSite = destinationSite;
    }

    public Site getDestinationSite() {
        return destinationSite;
    }

    public void setPassenger(AppUser passenger) {
        this.passenger = passenger;
    }

    public AppUser getPassenger() {
        return passenger;
    }


}