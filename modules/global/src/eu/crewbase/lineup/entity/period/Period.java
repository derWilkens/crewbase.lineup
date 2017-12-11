package eu.crewbase.lineup.entity.period;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;

import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

@NamePattern("%s %s |startDate,endDate")
@MappedSuperclass
public class Period extends StandardClientEntity {
	private static final long serialVersionUID = -5029609650607107962L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	protected Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	protected Date endDate;

	@Column(name = "REMARK")
	protected String remark;

	public void readDto(PeriodJsonDTO dto) {
		if (dto.getEntityId() != null) {
			this.id = dto.getEntityId();
		}
		this.endDate = dto.getEndDate();
		this.startDate = dto.getStartDate();
	}
	public Boolean isValid(){
		return (endDate != null && startDate != null);
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

}