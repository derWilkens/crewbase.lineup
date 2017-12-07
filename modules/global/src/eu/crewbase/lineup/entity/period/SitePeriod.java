package eu.crewbase.lineup.entity.period;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.Site;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "LINEUP_SITE_PERIOD")
@Entity(name = "lineup$SitePeriod")
@NamePattern(" - , %s|site")
public abstract class SitePeriod extends Period {
	private static final long serialVersionUID = 848103393103367871L;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	protected Site site;
	public void readDto(PeriodJsonDTO dto) {
		super.readDto(dto);
		this.site = dto.getOperationPeriod().site;
	}
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}