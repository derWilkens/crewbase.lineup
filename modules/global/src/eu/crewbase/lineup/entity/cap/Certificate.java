package eu.crewbase.lineup.entity.cap;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.entity.cap.coredata.QualificationType;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

@NamePattern("%s %s|certificateType,issuingDate")
@Table(name = "LINEUP_CERTIFICATE")
@Entity(name = "lineup$Certificate")
public class Certificate extends StandardClientEntity {
	private static final long serialVersionUID = 1013601858224666292L;

	@Temporal(TemporalType.DATE)
	@Column(name = "ISSUING_DATE")
	protected Date issuingDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRATION_DATE")
	protected Date expirationDate;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VERFIED_BY_ID")
	protected User verfiedBy;

	@Lookup(type = LookupType.DROPDOWN)
	@OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUALIFICATION_TYPE_ID")
	protected QualificationType qualificationType;

	@Lookup(type = LookupType.SCREEN, actions = {"lookup", "clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
	@OnDelete(DeletePolicy.UNLINK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APP_USER_ID")
	protected AppUser appUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_DATA_ID")
	protected FileDescriptor fileData;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CERTIFICATE_TYPE_ID")
	protected QualificationType certificateType;

	@Column(name = "STATE")
	protected String state;

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public FileDescriptor getFileData() {
		return fileData;
	}

	public void setFileData(FileDescriptor fileData) {
		this.fileData = fileData;
	}

	public void setQualificationType(QualificationType qualificationType) {
		this.qualificationType = qualificationType;
	}

	public QualificationType getQualificationType() {
		return qualificationType;
	}

	public QualificationType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(QualificationType certificateType) {
		this.certificateType = certificateType;
	}

	public void setIssuingDate(Date issuingDate) {
		this.issuingDate = issuingDate;
	}

	public Date getIssuingDate() {
		return issuingDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setVerfiedBy(User verfiedBy) {
		this.verfiedBy = verfiedBy;
	}

	public User getVerfiedBy() {
		return verfiedBy;
	}

}