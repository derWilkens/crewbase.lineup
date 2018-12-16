package eu.crewbase.lineup.entity.wayfare;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import eu.crewbase.lineup.entity.coredata.Site;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s - %s|startSite,destination")
@Table(name = "LINEUP_FAVORTITE_TRIPS")
@Entity(name = "lineup$FavortiteTrips")
public class FavortiteTrips extends StandardEntity {
    private static final long serialVersionUID = -1426158774165112345L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "START_SITE_ID")
    protected Site startSite;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DESTINATION_ID")
    protected Site destination;

    @Column(name = "EMAIL_NOTIFICATION")
    protected Boolean emailNotification;

    @Column(name = "ROUND_TRIP")
    protected Boolean roundTrip;

    public void setStartSite(Site startSite) {
        this.startSite = startSite;
    }

    public Site getStartSite() {
        return startSite;
    }

    public void setDestination(Site destination) {
        this.destination = destination;
    }

    public Site getDestination() {
        return destination;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setRoundTrip(Boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public Boolean getRoundTrip() {
        return roundTrip;
    }


}