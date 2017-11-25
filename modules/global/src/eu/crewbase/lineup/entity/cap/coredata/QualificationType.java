package eu.crewbase.lineup.entity.cap.coredata;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

@NamePattern("%s|name")
@Table(name = "LINEUP_QUALIFICATION_TYPE")
@Entity(name = "lineup$QualificationType")
public class QualificationType extends StandardClientEntity {
	private static final long serialVersionUID = 154912806578790643L;

	@Column(name = "NAME")
	protected String name;

	@Column(name = "VALIDITY")
	protected Integer validity;

	@OneToMany(mappedBy = "qualificationType")
	protected List<RoleQualificationType> roleQualificationType;

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	public Integer getValidity() {
		return validity;
	}

	public void setRoleQualificationType(List<RoleQualificationType> roleQualificationType) {
		this.roleQualificationType = roleQualificationType;
	}

	public List<RoleQualificationType> getRoleQualificationType() {
		return roleQualificationType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}