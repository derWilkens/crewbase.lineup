package eu.crewbase.lineup.listener;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

import eu.crewbase.lineup.entity.coredata.DutyPeriodTemplate;

@Component("LINEUP_DutyPeriodTemplateListener")
public class DutyPeriodTemplateListener implements BeforeInsertEntityListener<DutyPeriodTemplate> {


    @Override
    public void onBeforeInsert(DutyPeriodTemplate entity, EntityManager entityManager) {
    	UserSessionSource session = AppBeans.get(UserSessionSource.class);
    	entity.setUser(session.getUserSession().getUser());
    }


}