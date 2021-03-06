package eu.crewbase.lineup.entity.period;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.dto.PeriodDTO;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "LINEUP_SHIFT_PERIOD")
@Entity(name = "lineup$ShiftPeriod")
@NamePattern(" - , |functionCategory,personOnDuty")
public class ShiftPeriod extends Period {
	private static final long serialVersionUID = 848103393103367871L;

	@Lookup(type = LookupType.SCREEN)
	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ON_DUTY_ID")
	protected AppUser personOnDuty;

	public void readDto(PeriodDTO dto) {
		super.readDto(dto);
		setPersonOnDuty(dto.getPersonOnDuty());
	}
	public Boolean isValid(){
		return (super.isValid() && personOnDuty != null);
	}
	public AppUser getPersonOnDuty() {
		return personOnDuty;
	}

	public void setPersonOnDuty(AppUser person) {
		this.personOnDuty = person;
	}

}