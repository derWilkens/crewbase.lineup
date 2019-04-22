package eu.crewbase.lineup.service;

import com.haulmont.chile.core.model.Instance;

public interface DeepLinkService {
    String NAME = "lineup_DeepLinkService";
    public String generateDeepLinkForEntity(Instance entityInstance); 
}