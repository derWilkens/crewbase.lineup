package eu.crewbase.lineup.entity.period;

import javax.persistence.Column;
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
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.entity.coredata.FunctionCategory;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;
import eu.crewbase.lineup.entity.coredata.PeriodKind;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Listeners("LINEUP_PeriodTemplateListener")
@NamePattern(" %s|site")
@Table(name = "LINEUP_PERIOD_TEMPLATE")
@Entity(name = "lineup$PeriodTemplate")
public class PeriodTemplate extends StandardClientEntity {
	private static final long serialVersionUID = 7361455352235329343L;

	

	@Column(name = "DEFAULT_DURATION")
    protected Integer defaultDuration;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	protected Site site;

	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	protected User user;

	@Transient
	@MetaProperty
	protected String color;

    @Column(name = "PERIOD_KIND", columnDefinition = "varchar(70)")
    protected String periodKind;

    public void setPeriodKind(PeriodKind periodKind) {
        this.periodKind = periodKind == null ? null : periodKind.getId();
    }

    public PeriodKind getPeriodKind() {
        return periodKind == null ? null : PeriodKind.fromId(periodKind);
    }


	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setDefaultDuration(Integer defaultDuration) {
		this.defaultDuration = defaultDuration;
	}

	public Integer getDefaultDuration() {
		return defaultDuration;
	}

	

	

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

}