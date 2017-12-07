package eu.crewbase.lineup.entity.period;

import java.util.Date;

import eu.crewbase.lineup.entity.coredata.AppUser;

public class PeriodJsonDTO {
	
	private Date startDate;
	private Date endDate;
	private Boolean itemIncomplete;
	private AppUser personOnDuty;
	private OperationPeriod operationPeriod;
	

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
	

}
