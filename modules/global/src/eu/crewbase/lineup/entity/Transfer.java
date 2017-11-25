/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity;

import java.util.List;

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

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREW_CHANGE_ID")
    protected CrewChange crewChange;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATED_BY_ID")
    protected Company operatedBy;

    @OrderBy("orderNo")
    @Composition
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "transfer")
    protected List<Waypoint> waypointList;

    @Lookup(type = LookupType.DROPDOWN, actions = {"clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODE_OF_TRANSFER_ID")
    protected ModeOfTransfer modeOfTransfer;


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







    public void setCrewChange(CrewChange crewChange) {
        this.crewChange = crewChange;
    }

    public CrewChange getCrewChange() {
        return crewChange;
    }

	public void addWaypoint(Site site, Integer position) {
		Waypoint wp = new Waypoint();
		//wp.setClient(1);
		wp.setSite(site);
	}





}