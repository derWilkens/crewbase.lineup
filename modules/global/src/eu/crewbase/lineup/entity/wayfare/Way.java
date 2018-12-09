package eu.crewbase.lineup.entity.wayfare;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import eu.crewbase.lineup.entity.coredata.Site;
import javax.persistence.Column;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@NamePattern("%s|departureSite")
@Table(name = "LINEUP_WAY")
@Entity(name = "lineup$Way")
public class Way extends StandardEntity {
    private static final long serialVersionUID = -1918817632677323910L;

    @Lookup(type = LookupType.DROPDOWN)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARRIVAL_SITE_ID")
    protected Waypoint arrivalSite;

    @Lookup(type = LookupType.DROPDOWN)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTURE_SITE_ID")
    protected Waypoint departureSite;

    @Column(name = "TRAVEL_TIME")
    protected Integer travelTime;

    @Column(name = "OCCUPIED_SEATS")
    protected Integer occupiedSeats;


    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_ID")
    protected Transfer transfer;

    public void setDepartureSite(Waypoint departureSite) {
        this.departureSite = departureSite;
    }

    public Waypoint getDepartureSite() {
        return departureSite;
    }


    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Transfer getTransfer() {
        return transfer;
    }


    public Waypoint getArrivalSite() {
        return arrivalSite;
    }

    public void setArrivalSite(Waypoint arrivalSite) {
        this.arrivalSite = arrivalSite;
    }


    public void setTravelTime(Integer travelTime) {
        this.travelTime = travelTime;
    }

    public Integer getTravelTime() {
        return travelTime;
    }

    public void setOccupiedSeats(Integer occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public Integer getOccupiedSeats() {
        return occupiedSeats;
    }



}