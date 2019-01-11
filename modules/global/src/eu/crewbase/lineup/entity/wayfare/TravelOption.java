package eu.crewbase.lineup.entity.wayfare;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.security.entity.User;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("lineup_TravelOptionListener")
@NamePattern(" %s|transfer")
@Table(name = "LINEUP_TRAVEL_OPTION")
@Entity(name = "lineup$TravelOption")
public class TravelOption extends StandardClientEntity {
    private static final long serialVersionUID = -948516011710115412L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_ID")
    protected Transfer transfer;

    @Column(name = "BOOKED_SEATS")
    protected Integer bookedSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAVORITE_TRIP_ID")
    protected FavoriteTrip favoriteTrip;

    @Column(name = "STATUS")
    protected Integer status;

    public void setBookedSeats(Integer bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public Integer getBookedSeats() {
        return bookedSeats;
    }


    public void setFavoriteTrip(FavoriteTrip favoriteTrip) {
        this.favoriteTrip = favoriteTrip;
    }

    public FavoriteTrip getFavoriteTrip() {
        return favoriteTrip;
    }


    public void setStatus(TravelOptionStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public TravelOptionStatus getStatus() {
        return status == null ? null : TravelOptionStatus.fromId(status);
    }




    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Transfer getTransfer() {
        return transfer;
    }


}