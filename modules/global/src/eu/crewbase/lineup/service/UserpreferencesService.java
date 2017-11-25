package eu.crewbase.lineup.service;

import java.util.List;
import java.util.UUID;

import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.Site;

public interface UserpreferencesService {
    String NAME = "lineup_UserpreferencesService";

    UserPreference getPreference(UserPreferencesContext context, UUID id);
    List<UserPreference> getPreferences(UserPreferencesContext context);
    UserPreference createPreference(UserPreferencesContext context, UUID entityId, String userValue);
    void deletePreferenceByEntity(UserPreferencesContext context, UUID entityId);
	String getSiteColorPreference(UUID siteId);
	String getSiteBackgroundColorPreferrence(UUID siteId);
	List<Site> getSites(UserPreferencesContext context);
	String getRoleColorPreference(UUID roleId);
	
}