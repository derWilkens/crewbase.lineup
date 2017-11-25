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
@NamePattern("%s |lastname")
@Table(name = "LINEUP_USER_IMPORT_STAGE")
@Entity(name = "lineup$UserImportStage")
public class UserImportStage extends BaseUuidEntity implements Updatable, Creatable {
    private static final long serialVersionUID = 2133165937821283408L;

    @Column(name = "FIRSTNAME", length = 20)
    protected String firstname;

    @Column(name = "LASTNAME", length = 50)
    protected String lastname;

    @Column(name = "EMAIL", length = 50)
    protected String email;

    @Column(name = "DEPARTMENT", length = 20)
    protected String department;

    @Column(name = "POSITION_", length = 50)
    protected String position;

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


    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
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