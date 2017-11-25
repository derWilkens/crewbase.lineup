package eu.crewbase.lineup.entity.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@NamePattern("%s|typeName")
@Table(name = "LINEUP_PERIOD_TYPE")
@Entity(name = "lineup$PeriodType")
public class PeriodType extends StandardClientEntity {
	private static final long serialVersionUID = 8534126243989364712L;

	@Column(name = "TYPE_NAME", length = 20)
	protected String typeName;

	@Lookup(type = LookupType.DROPDOWN, actions = { "lookup" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_TYPE_ID")
	protected PeriodType parentType;

	@Column(name = "PERIOD_SUB_CLASS")
	protected String periodSubClass;

	public PeriodSubClass getPeriodSubClass() {
		return periodSubClass == null ? null : PeriodSubClass.fromId(periodSubClass);
	}

	public void setPeriodSubClass(PeriodSubClass periodSubClass) {
		this.periodSubClass = periodSubClass == null ? null : periodSubClass.getId();
	}

	public void setParentType(PeriodType parentType) {
		this.parentType = parentType;
	}

	public PeriodType getParentType() {
		return parentType;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

}