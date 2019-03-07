package eu.crewbase.lineup.service;

import com.haulmont.cuba.core.entity.Entity;

public interface EntityService {
    String NAME = "lineup_EntityService";
    
	<T extends Entity<K>, K> T getById(Class<T> entityClass, K id);

	void remove(Entity<?> entity);

    
}