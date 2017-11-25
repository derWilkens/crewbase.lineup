package eu.crewbase.lineup.entity;

import javax.annotation.Nullable;

import com.haulmont.chile.core.datatypes.impl.EnumClass;


public enum UserPreferencesContext implements EnumClass<Integer> {

    CampaignBrowse(10),
    Rotaplan(20),
    SiteColor(30),
    SiteBackgroundColor(35),
    SiteRotaplan(40),
    SiteCampaign(50),
    SiteEml(55),
    RotaplanDisplayCampaigns(60),
    RotaplanDepartments(70),
    RotaplanUsers(80),
    RotaplanStandardDuties(90),
    EmlDisplaySite(100), 
    RoleBackgroundColor(110);
	

    private Integer id;

    UserPreferencesContext(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static UserPreferencesContext fromId(Integer id) {
        for (UserPreferencesContext at : UserPreferencesContext.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}