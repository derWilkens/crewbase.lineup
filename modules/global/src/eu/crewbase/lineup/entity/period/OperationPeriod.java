package eu.crewbase.lineup.entity.period;

import com.haulmont.chile.core.annotations.NamePattern;
import eu.crewbase.lineup.entity.dto.PeriodDTO;

import javax.persistence.*;
import java.util.List;

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

	public void readDto(PeriodDTO dto) {
		super.readDto(dto);
	}

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