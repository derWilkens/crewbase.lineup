/*
 * Copyright (c) 2016 saas
 */
package eu.crewbase.lineup.entity.coredata;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.cap.coredata.Jobfunction;

/**
 * @author christian
 */
@NamePattern("%s, %s|lastname,firstname")
@Table(name = "LINEUP_APP_USER")
@Entity(name = "lineup$AppUser")
public class AppUser extends StandardEntity {
	private static final long serialVersionUID = 6123712345961441544L;

	@Column(name = "CLIENT")
	protected Integer client;

	@Column(name = "LASTNAME", length = 50)
	protected String lastname;

	@Column(name = "FIRSTNAME", length = 50)
	protected String firstname;

	@Column(name = "EMAIL", length = 100)
	protected String email;

	@JoinTable(name = "LINEUP_APP_USER_JOBFUNCTION_LINK", joinColumns = @JoinColumn(name = "APP_USER_ID"), inverseJoinColumns = @JoinColumn(name = "JOBFUNCTION_ID"))
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToMany
	protected List<Jobfunction> jobfunction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	protected Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	protected Department department;

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}

	public void setJobfunction(List<Jobfunction> jobfunction) {
		this.jobfunction = jobfunction;
	}

	public List<Jobfunction> getJobfunction() {
		return jobfunction;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public Integer getClient() {
		return client;
	}

}