/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.Company;
import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.ModeOfTransfer;
import eu.crewbase.lineup.entity.coredata.Site;

/**
 * @author christian
 */
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@Table(name = "LINEUP_TRANSFER")
@Entity(name = "lineup$Transfer")
public class Transfer extends Standstill {
	private static final long serialVersionUID = -5709533341256299692L;

	@Column(name = "TRANSFER_ORDER_NO", nullable = false)
	protected Integer transferOrderNo;

	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.CASCADE)
	@OneToMany(mappedBy = "transfer", cascade = CascadeType.PERSIST)
	protected List<Ticket> tickets;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ANCHOR_WAYPOINT_ID")
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.CASCADE)
	protected AnchorWaypoint anchorWaypoint;

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

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public AnchorWaypoint getAnchorWaypoint() {
		return anchorWaypoint;
	}

	public void setAnchorWaypoint(AnchorWaypoint anchorWaypoint) {
		this.anchorWaypoint = anchorWaypoint;
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
		Standstill currentStandstill = this.getAnchorWaypoint();
		Standstill nextStandstill = this.getAnchorWaypoint().nextWaypoint;
		do {
			totalDistance = totalDistance + currentStandstill.getSite().getDistanceTo(nextStandstill.getSite());
			currentStandstill = nextStandstill;
			nextStandstill = currentStandstill.getNextWaypoint();
			if (nextStandstill == null) {
				// der letze Waypoint hat keinen NextWaypoint - aber die Tour
				// geht zurück zum Anchorpoint
				totalDistance = totalDistance + currentStandstill.getSite().getDistanceTo(anchorWaypoint.getSite());
			}
		} while (nextStandstill != null);
		return totalDistance;

	}

	public String getRoute() {
		String route = "";
		String delim = "";
		Standstill currentStandstill = this.getAnchorWaypoint();
		 
		do {
			route = route + delim + currentStandstill.getSite().getItemDesignation();
			currentStandstill = currentStandstill.getNextWaypoint();
			if (currentStandstill == null) {
				// der letze Waypoint hat keinen NextWaypoint - aber die Tour
				// geht zurück zum Anchorpoint
				route = route + delim + anchorWaypoint.getSite().getItemDesignation();
			}
			delim = " - ";
		} while (currentStandstill != null);
		return route;
	}

	public List<Site> getSites() {
		List<Site> siteList = new ArrayList<Site>();
		Standstill currentStandstill = this.getAnchorWaypoint();
		do {
			siteList.add(currentStandstill.getSite());
			currentStandstill = currentStandstill.getNextWaypoint();
			if (currentStandstill == null) {
				// der letze Waypoint hat keinen NextWaypoint - aber die Tour
				// geht zurück zum Anchorpoint
				siteList.add(anchorWaypoint.getSite());
			}
		} while (currentStandstill != null);
		return siteList;
	}
	public HashMap<UUID,Site> getSiteHash(){
		HashMap<UUID,Site> siteHash = new HashMap<UUID,Site>();
		Standstill currentStandstill = this.getAnchorWaypoint();
		do {
			siteHash.put(currentStandstill.getSite().getUuid(),currentStandstill.getSite());
			currentStandstill = currentStandstill.getNextWaypoint();

		} while (currentStandstill != null);
		return siteHash;
	}
	public Transfer(List<Site> siteList) {
		Standstill currentStandstill = null;
		for (Site site : siteList) {

			if (this.getAnchorWaypoint() == null) {
				AnchorWaypoint awp = new AnchorWaypoint();
				awp.setSite(site);
				this.setAnchorWaypoint(awp);
				currentStandstill = awp;
				/**
				 * die letzte Site ist eigentlich ein AWP - muss der noch
				 * eingefügt werden?
				 */
				// }else
				// if(!tmpTransfer.getAnchorWaypoint().getSite().getId().equals(site.getId())){
			} else {
				Waypoint wp = new Waypoint();
				wp.setSite(site);
				currentStandstill.setNextWaypoint(wp);
				currentStandstill = wp;
			}
		}
	}

	public Transfer getTransientCopy() {
		Transfer copy = new Transfer(this.getSites());
		copy.setCraftType(getCraftType());
		return copy;
	}
}