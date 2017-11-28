package eu.crewbase.lineup.entity.period;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern(" - , |start,end")
@Table(name = "LINEUP_ABSENCE_PERIOD")
@Entity(name = "lineup$AbsencePeriod")
public class AbsencePeriod extends ShiftPeriod {
	private static final long serialVersionUID = 848103393103367871L;

}