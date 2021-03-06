/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import eu.crewbase.lineup.entity.coredata.Site;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author christian
 */
@Table(name = "LINEUP_WAYPOINT")
@NamePattern("  |")
@Entity(name = "lineup$Waypoint")
public class Waypoint extends StandardEntity {
	private static final long serialVersionUID = -4973545925621830772L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	protected Site site;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TAKE_OFF")
	protected Date takeOff;

	@Column(name = "STOPOVER_TIME")
	protected Integer stopoverTime;

	@Column(name = "POS_ORDER")
	protected Integer posOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSFER_ID")
	protected Transfer transfer;

	public Date getTakeOff() {
		return takeOff;
	}

	public void setTakeOff(Date takeOff) {
		this.takeOff = takeOff;
	}

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

    public void calculateTakeOff(Waypoint preWaypoint) {
		final int distanceTo = preWaypoint.getSite().getDistanceTo(this.getSite());
		int duration = calculateTravelDuration(distanceTo);
		if(this.getStopoverTime()!=null){
			duration = duration + this.getStopoverTime();
		}
		this.setTakeOff(addMinutes(preWaypoint.getTakeOff(), duration) );
    }

	private Date addMinutes(Date date, int minutes) {
		if(date == null) date = new Date();//für dummy Berechnungen
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, (minutes));
		return c.getTime();
	}
	private int calculateTravelDuration(int distanceTo) {
		int maxSpeed = 200; //km/h
		int duration = (int) ((Float.valueOf(distanceTo) / Float.valueOf(maxSpeed) * 60));
		return roundUp(duration, 5);
	}

	private int roundUp(int duration, int i) {
		return duration - duration % i + i;
	}
}