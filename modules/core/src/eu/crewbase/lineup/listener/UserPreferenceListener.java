package eu.crewbase.lineup.listener;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

import eu.crewbase.lineup.entity.UserPreference;

@Component("LINEUP_UserPreferenceListener")
public class UserPreferenceListener implements BeforeInsertEntityListener<UserPreference> {


    @Override
    public void onBeforeInsert(UserPreference entity, EntityManager entityManager) {
    	UserSessionSource session = AppBeans.get(UserSessionSource.class);
    	entity.setUserId(session.getUserSession().getUser().getId());
    }


}