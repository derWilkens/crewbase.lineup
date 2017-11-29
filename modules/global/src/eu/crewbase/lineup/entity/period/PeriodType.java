package eu.crewbase.lineup.entity.period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;
import eu.crewbase.lineup.entity.coredata.PeriodKind;

@NamePattern("%s|typeName")
@Table(name = "LINEUP_PERIOD_TYPE")
@Entity(name = "lineup$PeriodType")
public class PeriodType extends StandardClientEntity {
	private static final long serialVersionUID = 8534126243989364712L;

	@Column(name = "TYPE_NAME", length = 20)
	protected String typeName;

	@Column(name = "PARENT_TYPE")
	protected String parentType;

	public PeriodKind getParentType() {
		return parentType == null ? null : PeriodKind.fromId(parentType);
	}

	public void setParentType(PeriodKind parentType) {
		this.parentType = parentType == null ? null : parentType.getId();
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

}