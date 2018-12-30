/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.coredata;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;

/**
 * @author christian
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamePattern("%s|siteName")
@Table(name = "LINEUP_SITE")
@Entity(name = "lineup$Site")
public class Site extends StandardEntity {
	private static final long serialVersionUID = -1161554407313338235L;

	@Column(name = "SITE_NAME", nullable = false, length = 50)
	protected String siteName;

	    @Column(name = "LATITUDE")
    protected Double latitude;

    @Column(name = "LONGITUDE")
    protected Double longitude;

@Column(name = "ITEM_DESIGNATION", length = 7)
	protected String itemDesignation;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_SITE_ID")
	protected Site parentSite;

	@Column(name = "SHORT_ITEM_DESIGNATION", length = 4)
	protected String shortItemDesignation;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	protected SiteCategory category;

	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_TYPE_ID")
	protected SiteType siteType;

	@OneToMany(mappedBy = "site")
	protected List<SiteRoleRule> siteRoleRules;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }


	public Site() {
		super();
		 
		org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener pc = new org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener(null, null, _persistence_primaryKey) {
			

		};
		//this._persistence_listener =  pc;

		// TODO Auto-generated constructor stub
	}

	public void setSiteRoleRules(List<SiteRoleRule> siteRoleRules) {
		this.siteRoleRules = siteRoleRules;
	}

	public List<SiteRoleRule> getSiteRoleRules() {
		return siteRoleRules;
	}

	public void setCategory(SiteCategory category) {
		this.category = category;
	}

	public SiteCategory getCategory() {
		return category;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}

	public SiteType getSiteType() {
		return siteType;
	}

	public void setShortItemDesignation(String shortItemDesignation) {
		this.shortItemDesignation = shortItemDesignation;
	}

	public String getShortItemDesignation() {
		return shortItemDesignation;
	}

	public void setItemDesignation(String itemDesignation) {
		this.itemDesignation = itemDesignation;
	}

	public String getItemDesignation() {
		return itemDesignation;
	}

	public void setParentSite(Site parentSite) {
		this.parentSite = parentSite;
	}

	public Site getParentSite() {
		return parentSite;
	}
    public int getDistanceTo(Site site) {
        // Implementation specified by TSPLIB http://www2.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/
        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
//        double latitudeDifference = site.getLatitude()- latitude;
//        double longitudeDifference = site.getLongitude() - longitude;
//        return Math.sqrt(
//                (latitudeDifference * latitudeDifference) + (longitudeDifference * longitudeDifference));
        
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(site.getLatitude()- latitude);
        double dLng = Math.toRadians(site.getLongitude() - longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(site.getLatitude())) * Math.cos(Math.toRadians(latitude)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return (int) (dist/1000);
    }
}