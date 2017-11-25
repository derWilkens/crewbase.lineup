package eu.crewbase.lineup.service;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;

import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.Department;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TimelineDTO;

@Service(RotaplanService.NAME)
public class RotaplanServiceBean extends PreferencesService implements RotaplanService {
	
	@Inject
	private Persistence persistence;
	
	@Override
	public Site getSiteByItemDesignation(String itemDesignation) {
		Site result = null;
		try (Transaction tx = persistence.createTransaction()) {
			
			String queryString = "select e from lineup$Site e where e.itemDesignation = :itemDesignation";

			TypedQuery<Site> query = persistence.getEntityManager().createQuery(queryString, Site.class);
			query.setParameter("itemDesignation", itemDesignation);
			result = query.getFirstResult();
			tx.commit();
		}
		return result;
	}
	public Collection<Site> getPreferredSites(){
		return super.getPreferredSites(persistence.getEntityManager(), UserPreferencesContext.Rotaplan);
	}
	
	

}