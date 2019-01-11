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
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.Site;

/**
 * @author christian
 */
@Table(name = "LINEUP_WAYPOINT")
@NamePattern(" %s |takeOff")
@Entity(name = "lineup$Waypoint")
public class Waypoint extends StandardEntity {
	private static final long serialVersionUID = -4973545925621830772L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	protected Site site;

	@Temporal(TemporalType.TIME)
	@Column(name = "TAKE_OFF")
	protected Date takeOff;

	@Column(name = "STOPOVER_TIME")
	protected Integer stopoverTime;

	@Column(name = "POS_ORDER")
	protected Integer posOrder;

	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSFER_ID")
	protected Transfer transfer;

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setPosOrder(Integer posOrder) {
		this.posOrder = posOrder;
	}

	public Integer getPosOrder() {
		return posOrder;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}

	public String toString() {
		if (site != null) {
			return "Waypoint: " + site.getItemDesignation();
		}
		return super.toString();
	}

	public void setStopoverTime(Integer stopoverTime) {
		this.stopoverTime = stopoverTime;
	}

	public Integer getStopoverTime() {
		return stopoverTime;
	}

	public void setTakeOff(Date takeOff) {
		this.takeOff = takeOff;
	}

	public Date getTakeOff() {
		return takeOff;
	}
}