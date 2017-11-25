package eu.crewbase.lineup.entity.coredata;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

@NamePattern("%s|name")
@Table(name = "LINEUP_DEPARTMENT")
@Entity(name = "lineup$Department")
public class Department extends StandardClientEntity {
	private static final long serialVersionUID = -7887700637280267255L;

	@Column(name = "NAME", length = 100)
	protected String name;

	@Lookup(type = LookupType.DROPDOWN, actions = { "lookup", "open", "clear" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_DEPARTMENT_ID")
	protected Department parentDepartment;

	@Lookup(type = LookupType.SCREEN, actions = { "lookup", "open", "clear" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEADER_ID")
	protected AppUser leader;

	@Lookup(type = LookupType.SCREEN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPUTY_LEADER_ID")
	protected AppUser deputyLeader;

	@Column(name = "ACRONYM", length = 15)
	protected String acronym;

	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@OneToMany(mappedBy = "parentDepartment")
	protected List<Department> subDepartment;

	@OneToMany(mappedBy = "department")
	protected List<AppUser> members;

	public void setMembers(List<AppUser> members) {
		this.members = members;
	}

	public List<AppUser> getMembers() {
		return members;
	}

	public AppUser getLeader() {
		return leader;
	}

	public void setLeader(AppUser leader) {
		this.leader = leader;
	}

	public AppUser getDeputyLeader() {
		return deputyLeader;
	}

	public void setDeputyLeader(AppUser deputyLeader) {
		this.deputyLeader = deputyLeader;
	}

	public void setSubDepartment(List<Department> subDepartment) {
		this.subDepartment = subDepartment;
	}

	public List<Department> getSubDepartment() {
		return subDepartment;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getAcronym() {
		return acronym;
	}

	public Department getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}