/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.Company;
import eu.crewbase.lineup.entity.coredata.ModeOfTransfer;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;
import javax.validation.constraints.NotNull;

import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.coredata.CraftType;

/**
 * @author christian
 */
@NamePattern("%s|id")
@Table(name = "LINEUP_TRANSFER")
@Entity(name = "lineup$Transfer")
public class Transfer extends StandardClientEntity {
	private static final long serialVersionUID = -5709533341256299692L;

	@Column(name = "TRANSFER_ORDER_NO", nullable = false)
	protected Integer transferOrderNo;

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

	@OrderBy("orderNo")
	@Composition
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.CASCADE)
	@OneToMany(mappedBy = "transfer", cascade = CascadeType.PERSIST)
	protected List<Waypoint> waypointList;

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

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.PERSIST)
    protected List<Way> ways;

    public void setWays(List<Way> ways) {
        this.ways = ways;
    }

    public List<Way> getWays() {
        return ways;
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
		// TODO Auto-generated constructor stub
		this.waypointList = new ArrayList<Waypoint>();
		this.ways = new ArrayList<>();
	}

	public Integer getTransferOrderNo() {
		return transferOrderNo;
	}

	public void setTransferOrderNo(Integer transferOrderNo) {
		this.transferOrderNo = transferOrderNo;
	}

	public List<Waypoint> getWaypointList() {
		return waypointList;
	}

	public void setWaypointList(List<Waypoint> waypointList) {
		this.waypointList = waypointList;
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

}