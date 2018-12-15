/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.wayfare;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author christian
 */
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@Table(name = "LINEUP_WAYPOINT")
@NamePattern(" %s |takeOff")
@Entity(name = "lineup$Waypoint")
public class Waypoint extends Standstill {
	private static final long serialVersionUID = -4973545925621830772L;

	@Temporal(TemporalType.TIME)
	@Column(name = "TAKE_OFF")
	protected Date takeOff;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PREVIOUS_STANDSTILL_ID")
	protected Standstill previousStandstill;

	@Column(name = "STOPOVER_TIME")
	protected Integer stopoverTime;
    public Standstill getPreviousStandstill() {
        return previousStandstill;
    }

    public void setPreviousStandstill(Standstill previousStandstill) {
        this.previousStandstill = previousStandstill;
        previousStandstill.setNextWaypoint(this);
    }

    public String toString(){
    	if(site != null){
    		return site.getItemDesignation();
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

	/**
	 * @return a positive number, the distance multiplied by 1000 to avoid
	 *         floating point arithmetic rounding errors
	 */
	public double getDistanceFromPreviousStandstill() {
		if (previousStandstill == null) {
			throw new IllegalStateException("This method must not be called when the previousStandstill ("
					+ previousStandstill + ") is not initialized yet.");
		}
		return getDistanceFrom(previousStandstill);
	}

	/**
	 * @param standstill
	 *            never null
	 * @return a positive number, the distance multiplied by 1000 to avoid
	 *         floating point arithmetic rounding errors
	 */
	public double getDistanceFrom(Standstill standstill) {
		return standstill.getSite().getDistanceTo(site);
	}

	/**
	 * @param standstill
	 *            never null
	 * @return a positive number, the distance multiplied by 1000 to avoid
	 *         floating point arithmetic rounding errors
	 */
	public double getDistanceTo(Standstill standstill) {
		return site.getDistanceTo(standstill.getSite());
	}
}