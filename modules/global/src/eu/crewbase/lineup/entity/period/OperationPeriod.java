package eu.crewbase.lineup.entity.period;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

@Table(name = "LINEUP_OPERATION_PERIOD")
@Entity(name = "lineup$OperationPeriod")
@NamePattern(" - , |")
public class OperationPeriod extends SitePeriod {
	private static final long serialVersionUID = 848103393103367871L;

	@OneToMany(mappedBy = "operationPeriod")
	protected List<AttendencePeriod> attendencePeriods;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_PERIOD_ID")
	protected OperationPeriod parentPeriod;

	public void setAttendencePeriods(List<AttendencePeriod> attendencePeriods) {
		this.attendencePeriods = attendencePeriods;
	}

	public List<AttendencePeriod> getAttendencePeriods() {
		return attendencePeriods;
	}

	public OperationPeriod getParentPeriod() {
		return parentPeriod;
	}

	public void setParentPeriod(OperationPeriod parentPeriod) {
		this.parentPeriod = parentPeriod;
	}

}