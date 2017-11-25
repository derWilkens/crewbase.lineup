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

@NamePattern("%s|categoryName")
@Table(name = "LINEUP_FUNCTION_CATEGORY")
@Entity(name = "lineup$FunctionCategory")
public class FunctionCategory extends StandardClientEntity {
	private static final long serialVersionUID = 8534126243989364712L;

	@Column(name = "CATEGORY_NAME", length = 50)
	protected String categoryName;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_TYPE_ID")
	protected FunctionCategory parentType;

	@Column(name = "PERIOD_SUB_CLASS")
	protected String periodSubClass;

	public FunctionCategory getParentType() {
		return parentType;
	}

	public void setParentType(FunctionCategory parentType) {
		this.parentType = parentType;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public PeriodSubClass getPeriodSubClass() {
		return periodSubClass == null ? null : PeriodSubClass.fromId(periodSubClass);
	}

	public void setPeriodSubClass(PeriodSubClass periodSubClass) {
		this.periodSubClass = periodSubClass == null ? null : periodSubClass.getId();
	}

}