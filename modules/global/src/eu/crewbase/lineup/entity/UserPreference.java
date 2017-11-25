package eu.crewbase.lineup.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.model.Instance.PropertyChangeEvent;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import eu.crewbase.lineup.entity.coredata.StandardClientEntity;

@Listeners("LINEUP_UserPreferenceListener")
@Table(name = "LINEUP_USER_PREFERENCE")
@Entity(name = "lineup$UserPreference")
public class UserPreference extends StandardClientEntity {
    private static final long serialVersionUID = -7830169924564467941L;

    @Column(name = "USER_ID", nullable = false)
    protected UUID userId;

    @Column(name = "ENTITY_UUID")
    protected UUID entityUuid;

    @Column(name = "USER_VALUE")
    protected String userValue;

    @Column(name = "CONTEXT_ID")
    protected Integer contextId;

    public UserPreference() {
		super();
		this.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				System.out.println(e.getProperty());
				
			}
		});
		// TODO Auto-generated constructor stub
	}

	public void setContextId(UserPreferencesContext contextId) {
        this.contextId = contextId == null ? null : contextId.getId();
    }

    public UserPreferencesContext getContextId() {
        return contextId == null ? null : UserPreferencesContext.fromId(contextId);
    }


    public void setUserValue(String userValue) {
        this.userValue = userValue;
    }

    public String getUserValue() {
        return userValue;
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }


    public UUID getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(UUID entityUuid) {
        this.entityUuid = entityUuid;
    }





}