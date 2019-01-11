package eu.crewbase.lineup.service;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.TravelOptionStatus;

@Component("lineup_TravelOptionListener")
public class TravelOptionListener implements BeforeUpdateEntityListener<TravelOption> {

	@Inject
	private TravelOptionService service;

    @Override
    public void onBeforeUpdate(TravelOption entity, EntityManager entityManager) {
    	
    	TravelOption oldItem = entityManager.find(TravelOption.class, entity.getId());
    	
    	if(!oldItem.getStatus().equals(TravelOptionStatus.Requested) && entity.getStatus().equals(TravelOptionStatus.Requested)){
    		//notifyScheduler
    	}
    	//Approved
    	if(!oldItem.getStatus().equals(TravelOptionStatus.Approved) && entity.getStatus().equals(TravelOptionStatus.Approved)){
    		service.bookSeats(entity);

    	}
    	

    }


}