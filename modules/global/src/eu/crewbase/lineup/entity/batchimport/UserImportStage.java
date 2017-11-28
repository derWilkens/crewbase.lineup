package eu.crewbase.lineup.entity.batchimport;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.Creatable;
import com.haulmont.cuba.core.entity.Updatable;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@NamePattern("%s |lastName,firstName")
@Table(name = "LINEUP_USER_IMPORT_STAGE")
@Entity(name = "lineup$UserImportStage")
public class UserImportStage extends BaseUuidEntity implements Updatable, Creatable {
    private static final long serialVersionUID = 2133165937821283408L;



    @Column(name = "FIRST_NAME", length = 20)
    protected String firstName;

    @Column(name = "LAST_NAME", length = 50)
    protected String lastName;

    @Column(name = "EMAIL", length = 50)
    protected String email;



    @Column(name = "DEPARTMENT_ACRONYM", length = 20)
    protected String departmentAcronym;

    @Column(name = "JOBTITLE", length = 50)
    protected String jobtitle;

    @Lob
    @Column(name = "IMPORT_LOG")
    protected String importLog;

    @Column(name = "UPDATE_TS")
    protected Date updateTs;

    @Column(name = "UPDATED_BY", length = 50)
    protected String updatedBy;

    @Column(name = "CREATE_TS")
    protected Date createTs;

    @Column(name = "CREATED_BY", length = 50)
    protected String createdBy;









    public void setDepartmentAcronym(String departmentAcronym) {
        this.departmentAcronym = departmentAcronym;
    }

    public String getDepartmentAcronym() {
        return departmentAcronym;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getJobtitle() {
        return jobtitle;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }




    public void setImportLog(String importLog) {
        this.importLog = importLog;
    }

    public String getImportLog() {
        return importLog;
    }



    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    @Override
    public Date getCreateTs() {
        return createTs;
    }


    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    @Override
    public Date getUpdateTs() {
        return updateTs;
    }




    
}