package eu.crewbase.lineup.entity.period;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;

import eu.crewbase.lineup.DateFormatter;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;
import eu.crewbase.lineup.entity.dto.PeriodDTO;

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

@Column(name = "COLOR")
	protected String color;

			public void setColor(String color) {
		if(color!=null&&color!=""){
		this.color = color;
		}
	}

	public String getColor() {
		return color;
	}

	public void readDto(PeriodDTO dto){
		if (dto.getEntityId() != null) {
			this.id = UUID.fromString(dto.getEntityId());
		}
		this.startDate = DateFormatter.jsonDateToDate(dto.getStartDate());
		this.endDate = DateFormatter.jsonDateToDate(dto.getEndDate());
		if (this.getEndDate() == null && dto.getDuration() > 0) {
			Calendar c = Calendar.getInstance();
			c.setTime(this.getStartDate());
			c.add(Calendar.DAY_OF_YEAR, dto.getDuration());
			this.setEndDate(c.getTime());
		}
		this.setColor(dto.getColor());
		this.setRemark(dto.getRemark());
	}

	public Boolean isValid() {
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