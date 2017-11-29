package eu.crewbase.lineup.listener;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

import eu.crewbase.lineup.entity.period.PeriodTemplate;

@Component("LINEUP_PeriodTemplateListener")
public class DutyPeriodTemplateListener implements BeforeInsertEntityListener<PeriodTemplate> {


    @Override
    public void onBeforeInsert(PeriodTemplate entity, EntityManager entityManager) {
    	UserSessionSource session = AppBeans.get(UserSessionSource.class);
    	entity.setUser(session.getUserSession().getUser());
    }


}