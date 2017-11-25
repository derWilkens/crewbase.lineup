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

/**
 * @author christian
 */
@NamePattern("%s|siteName")
@Table(name = "LINEUP_SITE")
@Entity(name = "lineup$Site")
public class Site extends StandardClientEntity {
	private static final long serialVersionUID = -1161554407313338235L;

	@Column(name = "SITE_NAME", nullable = false, length = 50)
	protected String siteName;

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

}