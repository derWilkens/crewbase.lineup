package eu.crewbase.lineup.entity.period;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.AppUser;

@MappedSuperclass
@NamePattern(" - , |functionCategory,personOnDuty")
public class ShiftPeriod extends Period {
	private static final long serialVersionUID = 848103393103367871L;

	@Lookup(type = LookupType.SCREEN)
	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ON_DUTY_ID")
	protected AppUser personOnDuty;

	public AppUser getPersonOnDuty() {
		return personOnDuty;
	}

	public void setPersonOnDuty(AppUser personOnDuty) {
		this.personOnDuty = personOnDuty;
	}

}