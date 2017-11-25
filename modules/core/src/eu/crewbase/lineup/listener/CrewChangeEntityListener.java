package eu.crewbase.lineup.listener;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import eu.crewbase.lineup.entity.CrewChange;
import eu.crewbase.lineup.entity.Transfer;
import eu.crewbase.lineup.entity.Waypoint;

@Component("LINEUP_CrewChangeEntityListener")
public class CrewChangeEntityListener implements BeforeInsertEntityListener<CrewChange>, BeforeUpdateEntityListener<CrewChange> {


    @Override
    public void onBeforeInsert(CrewChange entity, EntityManager entityManager) {
    	persistNested(entity, entityManager);
    }


    @Override
    public void onBeforeUpdate(CrewChange entity, EntityManager entityManager) {
    	persistNested(entity, entityManager);
    }
    
    private void persistNested(CrewChange entity, EntityManager entityManager){
    	for (Waypoint wp : entity.getTransfers().iterator().next().getWaypointList()){
    		entityManager.persist(wp);
    	}
    	for (Transfer transfer : entity.getTransfers()){
    		entityManager.persist(transfer);
    	}
    }

}