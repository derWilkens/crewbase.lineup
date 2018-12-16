package eu.crewbase.lineup.entity.wayfare;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import eu.crewbase.lineup.entity.coredata.Site;
import javax.persistence.MappedSuperclass;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorColumn;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "LINEUP_STANDSTILL")
@Entity(name = "lineup$Standstill")
@NamePattern("%s|site")
public class Standstill extends StandardEntity {
    private static final long serialVersionUID = -6930929435066982182L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_ID")
    protected Transfer transfer;

    @OnDelete(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "NEXT_WAYPOINT_ID")
    protected Waypoint nextWaypoint;

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setNextWaypoint(Waypoint nextWaypoint) {
        this.nextWaypoint = nextWaypoint;
    }

    public Waypoint getNextWaypoint() {
        return nextWaypoint;
    }


}