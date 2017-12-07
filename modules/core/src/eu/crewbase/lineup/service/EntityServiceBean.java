package eu.crewbase.lineup.service;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;

import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.period.AbsencePeriod;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.ShiftPeriod;

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
}