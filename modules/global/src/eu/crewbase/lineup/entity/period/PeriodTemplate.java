package eu.crewbase.lineup.entity.period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.entity.coredata.PeriodKind;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Listeners("LINEUP_PeriodTemplateListener")
@NamePattern(" %s|site,periodKind")
@Table(name = "LINEUP_PERIOD_TEMPLATE")
@Entity(name = "lineup$PeriodTemplate")
public class PeriodTemplate extends StandardClientEntity {
	private static final long serialVersionUID = 7361455352235329343L;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	protected Site site;

	@Column(name = "REMARK")
	protected String remark;

	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	protected User user;

	@Column(name = "PERIOD_KIND", columnDefinition = "varchar(70)")
    protected String periodKind;

	@Column(name = "DURATION1")
	protected Integer duration1;

	@Column(name = "DURATION2")
	protected Integer duration2;

	@Column(name = "DURATION3")
	protected Integer duration3;

	@Column(name = "COLOR")
	protected String color;

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setDuration1(Integer duration1) {
		this.duration1 = duration1;
	}

	public Integer getDuration1() {
		return duration1;
	}

	public void setDuration2(Integer duration2) {
		this.duration2 = duration2;
	}

	public Integer getDuration2() {
		return duration2;
	}

	public void setDuration3(Integer duration3) {
		this.duration3 = duration3;
	}

	public Integer getDuration3() {
		return duration3;
	}

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

	public String getSiteId() {
		if (site != null) {
			return site.getId().toString();
		}
		return null;
	}

}