package eu.crewbase.lineup.entity.wayfare;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.PrimaryKeyJoinColumn;

@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
@Table(name = "LINEUP_ANCHOR_WAYPOINT")
@Entity(name = "lineup$AnchorWaypoint")
public class AnchorWaypoint extends Standstill {
	private static final long serialVersionUID = -336246300143108768L;

	/**
	 * @param standstill
	 *            never null
	 * @return a positive number, the distance multiplied by 1000 to avoid
	 *         floating point arithmetic rounding errors
	 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE_TIME")
    protected Date startDateTime;

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }


	public double getDistanceTo(Standstill standstill) {
		return site.getDistanceTo(standstill.getSite());
	}
}