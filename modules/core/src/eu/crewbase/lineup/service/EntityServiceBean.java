package eu.crewbase.lineup.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;

@Service(EntityService.NAME)
public class EntityServiceBean implements EntityService {
	
	@Inject
	private Persistence persistence;

	@Override
	public <T extends Entity<K>, K> T getById(Class<T> entityClass, K id){
		try (Transaction tx = persistence.createTransaction()) {
			return persistence.getEntityManager().find(entityClass, id);

		}
	}
	@Override
	public void remove(Entity<?> entity){
		try (Transaction tx = persistence.createTransaction()) {
			
			 persistence.getEntityManager().remove(getById(entity.getClass(), entity.getId()));
			 tx.commit();
		}
	}
}