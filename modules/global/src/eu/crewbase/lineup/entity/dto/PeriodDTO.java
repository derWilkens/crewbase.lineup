package eu.crewbase.lineup.entity.dto;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Site;

@NamePattern("%s %s|clazzName,userId")
@MetaClass(name = "lineup$PeriodDTO")
public class PeriodDTO extends BaseUuidEntity {
	private static final long serialVersionUID = 1108090666040034053L;

	@MetaProperty
	protected String userId;

	@MetaProperty
	protected String startDate;

	@MetaProperty
	protected String endDate;

	@MetaProperty
	protected Integer duration;

	@MetaProperty
	protected String remark;

	@MetaProperty
	protected String siteId;

	@MetaProperty
	protected String color;

	@MetaProperty
	protected String clazzName;

	@MetaProperty
	protected String entityId;
	
	private AppUser appUser;
	private Site site;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	//"yyyy-MM-dd"
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setPersonOnDuty(AppUser user) {
		this.appUser = user;

	}

	public AppUser getPersonOnDuty() {
		return appUser;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

}