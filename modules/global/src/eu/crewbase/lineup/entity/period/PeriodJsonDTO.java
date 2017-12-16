package eu.crewbase.lineup.entity.period;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Site;

public class PeriodJsonDTO {
	private UUID entityId;
	private String clazzName;
	private Date startDate;
	private Date endDate;
	private Boolean itemIncomplete = true;
	private AppUser personOnDuty;
	private OperationPeriod operationPeriod;
	private Site site;
	private int duration;
	private String color;
	private String remark;

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
		if (this.getEndDate() == null && duration > 0) {
			Calendar c = Calendar.getInstance();
			c.setTime(this.getStartDate());
			c.add(Calendar.DAY_OF_YEAR, duration);
			this.setEndDate(c.getTime());
		}
	}

	public UUID getEntityId() {
		return entityId;
	}

	public void setEntityId(UUID entityId) {
		this.entityId = entityId;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getItemIncomplete() {
		
		return itemIncomplete;
	}

	public void setItemIncomplete(Boolean itemIncomplete) {
		this.itemIncomplete = itemIncomplete;
	}

	public AppUser getPersonOnDuty() {
		return personOnDuty;
	}

	public void setPersonOnDuty(AppUser personOnDuty) {
		this.personOnDuty = personOnDuty;
	}

	public OperationPeriod getOperationPeriod() {
		return operationPeriod;
	}

	public void setOperationPeriod(OperationPeriod operationPeriod) {
		this.operationPeriod = operationPeriod;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
