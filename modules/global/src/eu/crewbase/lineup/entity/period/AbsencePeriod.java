package eu.crewbase.lineup.entity.period;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern(" - , |start,end")
@Table(name = "LINEUP_ABSENCE_PERIOD")
@Entity(name = "lineup$AbsencePeriod")
public class AbsencePeriod extends ShiftPeriod {
private static final long serialVersionUID = 848103393103367871L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ABSENCE_TYPE_ID")
    
	public void readDto(PeriodJsonDTO dto) {
		super.readDto(dto);
	}
    
    protected PeriodType absenceType;

	    public void setAbsenceType(PeriodType absenceType) {
        this.absenceType = absenceType;
    }

    public PeriodType getAbsenceType() {
        return absenceType;
    }



}