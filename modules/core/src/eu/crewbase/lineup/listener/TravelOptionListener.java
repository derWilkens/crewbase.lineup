package eu.crewbase.lineup.listener;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.TravelOptionStatus;
import eu.crewbase.lineup.service.TravelOptionService;

//@Component("lineup_TravelOptionListener")
public class TravelOptionListener implements BeforeUpdateEntityListener<TravelOption> {

//	@Inject
	public TravelOptionService service;

//    @Override
    public void onBeforeUpdate(TravelOption entity, EntityManager entityManager) {
    	
    	TravelOption oldItem = entityManager.find(TravelOption.class, entity.getId());
    	
      	if(oldItem!= null && entity.getStatus().equals(TravelOptionStatus.Init)){
    		//TravelOptions werden erzeugt, der Minute-Scheduler kommt vorbei und sendet Mail raus
    	}
      	
      	else if(!oldItem.getStatus().equals(TravelOptionStatus.Requested) && entity.getStatus().equals(TravelOptionStatus.Requested)){
    		//notifyScheduler
      		
    	}
    	//Approved
      	else if(!oldItem.getStatus().equals(TravelOptionStatus.Approved) && entity.getStatus().equals(TravelOptionStatus.Approved)){
    		//service.bookSeats(entity.getId(), );

    	}
    	

    }


}