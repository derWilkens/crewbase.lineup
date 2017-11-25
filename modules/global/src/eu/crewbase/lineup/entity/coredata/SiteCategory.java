package eu.crewbase.lineup.entity.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@NamePattern("%s|name")
@Table(name = "LINEUP_SITE_CATEGORY")
@Entity(name = "lineup$SiteCategory")
public class SiteCategory extends StandardEntity {
	private static final long serialVersionUID = 8818245036847572405L;

	@Column(name = "NAME", length = 50)
	protected String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}