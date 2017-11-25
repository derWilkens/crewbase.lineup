package eu.crewbase.lineup.entity.cap.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

@NamePattern("%s %s|role,qualificationType")
@Table(name = "LINEUP_ROLE_QUALIFICATION_TYPE")
@Entity(name = "lineup$RoleQualificationType")
public class RoleQualificationType extends StandardClientEntity {
	private static final long serialVersionUID = -31684618227061457L;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	protected Role role;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.CASCADE)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "QUALIFICATION_TYPE_ID")
	protected QualificationType qualificationType;

	@Column(name = "MANDATORY")
	protected Boolean mandatory;

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setQualificationType(QualificationType qualificationType) {
		this.qualificationType = qualificationType;
	}

	public QualificationType getQualificationType() {
		return qualificationType;
	}

}