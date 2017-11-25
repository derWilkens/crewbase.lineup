package eu.crewbase.lineup.entity.period;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import eu.crewbase.lineup.entity.coredata.FunctionCategory;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

@NamePattern("%s %s %s|start,end,functionCategory")
@MappedSuperclass
public class Period extends StandardClientEntity {
	private static final long serialVersionUID = -5029609650607107962L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_")
	protected Date start;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_")
	protected Date end;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNCTION_CATEGORY_ID")
	protected FunctionCategory functionCategory;

	@Column(name = "REMARK")
	protected String remark;

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setFunctionCategory(FunctionCategory functionCategory) {
		this.functionCategory = functionCategory;
	}

	public FunctionCategory getFunctionCategory() {
		return functionCategory;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStart() {
		return start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getEnd() {
		return end;
	}

}