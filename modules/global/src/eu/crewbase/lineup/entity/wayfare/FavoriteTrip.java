package eu.crewbase.lineup.entity.wayfare;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import eu.crewbase.lineup.entity.coredata.Site;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.OneToMany;

@NamePattern("%s - %s|startSite,destination")
@Table(name = "LINEUP_FAVORITE_TRIP")
@Entity(name = "lineup$FavoriteTrip")
public class FavoriteTrip extends StandardEntity {
    private static final long serialVersionUID = -1426158774165112345L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "START_SITE_ID")
    protected Site startSite;

    @Column(name = "EXACT_MATCH")
    protected Boolean exactMatch;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DESTINATION_ID")
    protected Site destination;

    @Column(name = "EMAIL_NOTIFICATION")
    protected Boolean emailNotification;

    @Column(name = "SEND_SUMMERY")
    protected Boolean sendSummery;

    @Column(name = "ROUND_TRIP")
    protected Boolean roundTrip;

    @OneToMany(mappedBy = "favoriteTrip")
    protected List<TravelOption> travelOption;

    public void setTravelOption(List<TravelOption> travelOption) {
        this.travelOption = travelOption;
    }

    public List<TravelOption> getTravelOption() {
        return travelOption;
    }


    public void setSendSummery(Boolean sendSummery) {
        this.sendSummery = sendSummery;
    }

    public Boolean getSendSummery() {
        return sendSummery;
    }


    public void setExactMatch(Boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public Boolean getExactMatch() {
        return exactMatch;
    }


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

    /**
     * Alle Sites eines CrewChanges werden überprüft, ob sie mit diesem Favoriten übereinstimmen
     * 
     * @param siteListTransfers
     * @return dieser Favorite besteht aus den Sites die auch im CrewChange enthalten sind
     */
	public boolean containsCrewChangeSites(List<Site> siteListTransfers) {
		boolean site1 = false;
				
		for (Site site : siteListTransfers) {
			
			//site1 und site2 müssen in der richtigen Reihenfolge in Liste sein
			if(site.getUuid().equals(this.getStartSite().getUuid())){
				site1 = true;
			}
			if(site1 && site.getUuid().equals(this.getDestination().getUuid())){
				return true;
			}
		}
		return false;
	}
	public String toString(){
		return "FavTrip: " + startSite.getItemDesignation() + " - " + destination.getItemDesignation();
	}

}