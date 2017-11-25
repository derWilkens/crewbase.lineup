package eu.crewbase.lineup.entity.dto;

import java.util.UUID;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

import eu.crewbase.lineup.entity.coredata.PeriodSubClass;

@NamePattern("%s|categoryName")
@MetaClass(name = "lineup$FunctionCategoryDTO")
public class FunctionCategoryDTO extends BaseUuidEntity {
    private static final long serialVersionUID = -966432658714943535L;

    @MetaProperty
    protected String categoryName;

    @MetaProperty
    protected String periodSubClass;

    public FunctionCategoryDTO(UUID id, String categoryName, PeriodSubClass periodSubClass) {
		super();
		super.id = id;
		this.categoryName = categoryName;
		this.periodSubClass = periodSubClass == null ? null : periodSubClass.getId();
	}

	public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setPeriodSubClass(PeriodSubClass periodSubClass) {
        this.periodSubClass = periodSubClass == null ? null : periodSubClass.getId();
    }

    public PeriodSubClass getPeriodSubClass() {
        return periodSubClass == null ? null : PeriodSubClass.fromId(periodSubClass);
    }


}