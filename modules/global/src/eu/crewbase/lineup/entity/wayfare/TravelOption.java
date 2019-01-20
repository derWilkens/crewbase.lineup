package eu.crewbase.lineup.entity.wayfare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

import eu.crewbase.lineup.entity.coredata.StandardClientEntity;
import eu.crewbase.lineup.entity.dto.TripDTO;

import java.util.List;
import javax.persistence.OneToMany;

//@Listeners("lineup_TravelOptionListener")
@NamePattern(" %s|transfer")
@Table(name = "LINEUP_TRAVEL_OPTION")
@Entity(name = "lineup$TravelOption")
public class TravelOption extends StandardClientEntity {
	private static final long serialVersionUID = -948516011710115412L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSFER_ID")
	protected Transfer transfer;

	@Column(name = "AVAILABLE_SEATS")
	protected Integer availableSeats;

	@Column(name = "BOOKED_SEATS")
	protected Integer bookedSeats;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FAVORITE_TRIP_ID")
	protected FavoriteTrip favoriteTrip;

	@OneToMany(mappedBy = "travelOption")
	protected List<Ticket> bookedTickets;

	@Column(name = "STATUS")
	protected Integer status;

	public void setBookedTickets(List<Ticket> bookedTickets) {
		this.bookedTickets = bookedTickets;
	}

	public List<Ticket> getBookedTickets() {
		return bookedTickets;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

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

	public void setAvailableSeats(List<TripDTO> groupedTickets) {
		this.availableSeats = transfer.getFreeCapacityForTrip(groupedTickets, this);
		
	}

}