/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

import eu.crewbase.lineup.entity.wayfare.Transfer;
import com.haulmont.chile.core.annotations.Composition;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * @author christian
 */
@NamePattern("%s %s |site,takeOff")
@Table(name = "LINEUP_WAYPOINT")
@Entity(name = "lineup$Waypoint")
public class Waypoint extends StandardClientEntity {
    private static final long serialVersionUID = -4973545925621830772L;

    @Temporal(TemporalType.TIME)
    @Column(name = "TAKE_OFF")
    protected Date takeOff;


    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_ID")
    protected Transfer transfer;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    @Column(name = "ORDER_NO")
    protected Integer orderNo;

    @Column(name = "STOPOVER_TIME")
    protected Integer stopoverTime;

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }




    public void setStopoverTime(Integer stopoverTime) {
        this.stopoverTime = stopoverTime;
    }

    public Integer getStopoverTime() {
        return stopoverTime;
    }


    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }



    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }













    public void setTakeOff(Date takeOff) {
        this.takeOff = takeOff;
    }

    public Date getTakeOff() {
        return takeOff;
    }


}