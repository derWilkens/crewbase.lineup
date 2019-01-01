package eu.crewbase.lineup.entity.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@NamePattern("%s|categoryName")
@Table(name = "LINEUP_SITE_CATEGORY")
@Entity(name = "lineup$SiteCategory")
public class SiteCategory extends StandardEntity {
private static final long serialVersionUID = 8818245036847572405L;

    @Column(name = "CATEGORY_NAME", length = 50)
    protected String categoryName;

	    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }


	
	
	
}