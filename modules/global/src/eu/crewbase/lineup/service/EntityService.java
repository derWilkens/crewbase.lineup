package eu.crewbase.lineup.service;

import java.util.UUID;

import com.haulmont.cuba.core.entity.Entity;

import eu.crewbase.lineup.entity.coredata.AppUser;

public interface EntityService {
    String NAME = "lineup_EntityService";
    
    public AppUser getAppUser(UUID appUserId);

	public <T extends Entity<K>, K> T getById(Class<T> entityClass, K id);
    
}