package eu.crewbase.lineup.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import com.haulmont.cuba.core.listener.AfterInsertEntityListener;
import java.sql.Connection;

@Component("lineup_CrewChangeListener")
public class CrewChangeListener implements BeforeInsertEntityListener<CrewChange>, AfterInsertEntityListener<CrewChange> {

	private static final Logger log = LoggerFactory.getLogger(CrewChangeListener.class);

    @Override
    public void onBeforeInsert(CrewChange entity, EntityManager entityManager) {
    		log.debug("------------------BEFORE INSERT CC");
    }


    @Override
    public void onAfterInsert(CrewChange entity, Connection connection) {
    	log.debug("------------------after INSERT CC");

    }


}