/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.minlog.Log;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.Company;
import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.ModeOfTransfer;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TripDTO;

/**
 * @author christian
 */
@Listeners({ "lineup_TransferListener" })
@Table(name = "LINEUP_TRANSFER")
@Entity(name = "lineup$Transfer")
public class Transfer extends StandardEntity {
	private static final long serialVersionUID = -5709533341256299692L;
	private static final Logger log = LoggerFactory.getLogger(Transfer.class);
	// private static final Logger log =
	// LoggerFactory.getLogger(Transfer.class);

	@Column(name = "TRANSFER_ORDER_NO", nullable = false)
	protected Integer transferOrderNo;

	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OrderBy("posOrder")
	@Composition
	@OnDelete(DeletePolicy.CASCADE)
	@OneToMany(mappedBy = "transfer", cascade = CascadeType.PERSIST)
	protected List<Waypoint> waypoints;

	@Composition
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.CASCADE)
	@OneToMany(mappedBy = "transfer", cascade = CascadeType.PERSIST)
	protected List<Ticket> tickets;

	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREW_CHANGE_ID")
	protected CrewChange crewChange;

	@Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATED_BY_ID")
	protected Company operatedBy;

	@Lookup(type = LookupType.DROPDOWN, actions = {"clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODE_OF_TRANSFER_ID")
	protected ModeOfTransfer modeOfTransfer;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CRAFT_TYPE_ID")
	protected CraftType craftType;

	public void setWaypoints(List<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}

	public List<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setCraftType(CraftType craftType) {
		this.craftType = craftType;
	}

	public CraftType getCraftType() {
		return craftType;
	}

	public CrewChange getCrewChange() {
		return crewChange;
	}

	public void setCrewChange(CrewChange crewChange) {
		this.crewChange = crewChange;
	}

	public Transfer() {
		this.tickets = new ArrayList<Ticket>();
		this.waypoints = new ArrayList<Waypoint>();
	}

	public Integer getTransferOrderNo() {
		return transferOrderNo;
	}

	public void setTransferOrderNo(Integer transferOrderNo) {
		this.transferOrderNo = transferOrderNo;
	}

	public void setModeOfTransfer(ModeOfTransfer modeOfTransfer) {
		this.modeOfTransfer = modeOfTransfer;
	}

	public ModeOfTransfer getModeOfTransfer() {
		return modeOfTransfer;
	}

	public Company getOperatedBy() {
		return operatedBy;
	}

	public void setOperatedBy(Company operatedBy) {
		this.operatedBy = operatedBy;
	}

	public int getTotalDistance() {
		int totalDistance = 0;
		Waypoint preWaypoint = null;
		Waypoint currentWaypoint = null;

		Iterator<Waypoint> it = this.getWaypoints().iterator();
		while (it.hasNext()) {
			if (preWaypoint == null) {
				preWaypoint = it.next();
			} else if (it.hasNext()) {
				currentWaypoint = it.next();
				totalDistance = totalDistance + preWaypoint.getSite().getDistanceTo(currentWaypoint.getSite());
				preWaypoint = currentWaypoint;
			}
		}
		return totalDistance;
	}

	public String getRouteShort() {
		String route = "";
		String delim = "";

		for (Waypoint waypoint : this.getWaypoints()) {
			route = route + delim + waypoint.getSite().getItemDesignation();
			delim = " - ";
		}
		return route;
	}

	public String getRoute() {
		String route = "";
		String delim = "";

		for (Waypoint waypoint : this.getWaypoints()) {
			route = route + delim + waypoint.getSite().getSiteName();
			;
			delim = " - ";
		}
		return route;
	}

	public List<Site> getSites() {
		return getWaypoints().stream().map(w -> w.getSite()).collect(Collectors.toList());
	}

	public HashMap<UUID, Site> getSiteHash() {
		HashMap<UUID, Site> resultMap = new HashMap<UUID, Site>();
		// return (HashMap<UUID, Site>)
		// getSites().stream().collect(Collectors.toMap(p.getId(), p -> p, (p1,
		// p2) -> p1));
		for (Waypoint waypoint : getWaypoints()) {
			resultMap.put(waypoint.getSite().getId(), waypoint.getSite());
		}
		return resultMap;
	}

	public void renumber() {
		for (Waypoint waypoint : this.getWaypoints()) {
			waypoint.setPosOrder(this.getWaypoints().indexOf(waypoint));
		}
	}

	// Wegpunkt wird so eingesetzt, dass die Strecke minimal ist
	// @fixme optimum wird erreicht, wenn PAX-Reisedauer UND Strecke minimal ist
	public boolean addWaypointShortestWay(Waypoint waypoint) {

		// schon drin, also true zurück
		if (this.getSiteHash().containsKey(waypoint.getSite().getId())) {
			return false;
		}

		waypoint.setTransfer(this);
		int len = this.getWaypoints().size();
		int optimalPosition = 0;

		CraftType craft = this.getCraftType();
		int minDistance = craft.getMaxRange();

		for (int i = 1; i < len; i++) {

			this.getWaypoints().add(i, waypoint);
			Log.debug(this.getRouteShort() + " Dist: " + this.getTotalDistance());

			// neue Distanz ist erreichbar und kleiner als optimum? Else
			// Wegpunkt wieder rausnehmen
			if (this.getTotalDistance() < craft.getMaxRange() && this.getTotalDistance() < minDistance) {
				minDistance = this.getTotalDistance();
				optimalPosition = i;
			}
			this.getWaypoints().remove(waypoint);
		}

		if (optimalPosition > 0) {
			this.addWaypointAt(waypoint, optimalPosition);
			return true;
		}
		return false;
	}

	public boolean addWaypointAt(Waypoint waypoint, int position) {
		waypoint.setTransfer(this);
		this.getWaypoints().add(position, waypoint);
		if(this.getTotalDistance() > this.getCraftType().getMaxRange()){
			this.getWaypoints().remove(position);
			return false;
		}
		this.renumber();
		return true;
	}

	public boolean isLastWaypoint(Waypoint waypoint) {
		return this.getWaypoints().get(this.getWaypoints().size() - 1).equals(waypoint);
	}
	
	/**
	 * Es werden die Tickets gruppiert nach Sites übergeben -> Tickets bilden
	 * Strecken ab mit entsprechenden gebuchten Plätzen
	 * 
	 */
	public int getFreeCapacityForTrip(List<TripDTO> groupedTickets, TravelOption travelOption) {
		return getFreeCapacityForTrip(groupedTickets, travelOption.getFavoriteTrip().getStartSite(),
						travelOption.getFavoriteTrip().getDestination());
	}
	public Integer getFreeCapacityForTrip(List<TripDTO> groupedTickets, FavoriteTrip favoriteTrip) {
		return getFreeCapacityForTrip(groupedTickets, favoriteTrip.getStartSite(),
				favoriteTrip.getDestination());
	}
	public int getFreeCapacityForTrip(List<TripDTO> groupedTickets, Site siteA, Site siteB) {

		HashMap<UUID, Integer> capaMap = getBookedSeatsMap(groupedTickets);

		// dann über Fav-Strecke und Result iterieren
		// int capa = transfer.getCraftType().getSeats();
		int bookedSeats = 0;
		// den kompletten Transfer durchlaufen und die minimale Kapazität auf
		// der desiredRoute ermitteln
		// Route: A (5) - B(5+2) - C(-5) - D(+10) - E
		// Kapa Strecke C - E ? Booked C (2) ...

		for (Waypoint waypoint : this.getWaypoints()) {
			if (!this.isLastWaypoint(waypoint)) {
				Site favSite = waypoint.getSite();

				boolean onboard = false;
				if (favSite.getId().equals(siteA.getId())) {
					onboard = true;
				}
				if (favSite.getId().equals(siteB.getId())) {
					onboard = false;
					log.debug(favSite.getItemDesignation() + ": an Bord: " + bookedSeats);
					break;
				}
				// es sind welche zugestiegen, hochzählen
				if (onboard && capaMap.get(favSite.getId()) != null && capaMap.get(favSite.getId()) > bookedSeats) {
					bookedSeats = bookedSeats + capaMap.get(favSite.getId());
				}

			}
		}

		log.debug("Booked in total: " + bookedSeats + " | Transfer: " + this.getRouteShort());
		return bookedSeats;
	}
	
	/**
	 * Für jede Site werden die Booked Seats berechnet. Für Ticketgruppe wird
	 * die Strecke durchlaufen und die besetzten Plätze aufaddiert.
	 */
	public HashMap<UUID, Integer> getBookedSeatsMap(List<TripDTO> groupedTickets) {

		HashMap<UUID, Integer> resultCapaMap = new HashMap<UUID, Integer>();

		// über die tickets iterieren

		for (TripDTO ticketGroup : groupedTickets) {
			log.debug("Booked Tickets " + ticketGroup.getSiteA().getItemDesignation() + " - "
					+ ticketGroup.getSiteB().getItemDesignation() + ": " + ticketGroup.getBookedSeats());

			// capaMap enthält booked seats beim Verlassen der Site:
			// über die Strecke iterieren, wenn site = startSite -> PAX steigen
			// zu, wenn Site = destination -> PAX steigen aus
			boolean onboard = false;

			// den letzten Waypoint nicht mehr berücksichtigen, weil die Site
			// normalerweise identisch mit dem Start ist
			for (int i = 0; i < this.getWaypoints().size() - 1; i++) {
				Waypoint waypoint = this.getWaypoints().get(i);
				Site site = waypoint.getSite();

				if (site.getId().equals(ticketGroup.getSiteB().getId()) && onboard) {
					onboard = false;
				} else if (site.getId().equals(ticketGroup.getSiteA().getId()) || onboard) {
					int currentBookedSeats = 0;
					if (resultCapaMap.containsKey(site.getId())) {
						currentBookedSeats = resultCapaMap.get(site.getId());
					}
					log.debug("Site: " + site.getItemDesignation() + " Seats already Booked: " + currentBookedSeats
							+ ", Seats to add: " + ticketGroup.getBookedSeats());
					resultCapaMap.put(site.getId(), currentBookedSeats + ticketGroup.getBookedSeats());
					onboard = true;
				}
			}
		}
		return resultCapaMap;
	}


}