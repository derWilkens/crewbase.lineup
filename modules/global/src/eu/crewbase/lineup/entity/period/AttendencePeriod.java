package eu.crewbase.lineup.entity.period;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.PeriodDTO;

@Listeners("lineup_AttendencePeriodListener")
@NamePattern(" - , |")
@Table(name = "LINEUP_ATTENDENCE_PERIOD")
@Entity(name = "lineup$AttendencePeriod")
public class AttendencePeriod extends ShiftPeriod {
	private static final long serialVersionUID = 848103393103367871L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATION_PERIOD_ID")
	protected OperationPeriod operationPeriod;
	@Lookup(type = LookupType.DROPDOWN)
	@Transient
	@MetaProperty
	protected Site site;

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}

	public void readDto(PeriodDTO dto) {
		super.readDto(dto);
		// this.operationPeriod = dto.getOperationPeriod();
		this.site = dto.getSite();
	}

	public Boolean isValid() {
		return (super.isValid() && operationPeriod != null);
	}

	public OperationPeriod getOperationPeriod() {
		return operationPeriod;
	}

	public void setOperationPeriod(OperationPeriod operationPeriod) {
		this.operationPeriod = operationPeriod;
	}

}