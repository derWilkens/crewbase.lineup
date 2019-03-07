package eu.crewbase.lineup.web.site;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.service.UserpreferencesService;

public class SitePeriodChoose extends AbstractLookup {

	@Inject
	private ComponentsFactory siteSelectedComponentsFactory;

	@Inject
	private CollectionDatasource<Site, UUID> sitesDs;

	@Inject
	private Table<Site> sitesTable;

	@Inject
	private UserpreferencesService preferencesService;
	
	private static UserPreferencesContext context = UserPreferencesContext.Rotaplan;

	@Override
	public void init(Map<String, Object> params) {
		sitesDs.refresh();
		List<UserPreference> preferredSitesRotaplan = preferencesService
				.getPreferences(context);

		sitesTable.addGeneratedColumn("selected", entity -> {
			CheckBox checkBox = siteSelectedComponentsFactory.createComponent(CheckBox.class);
			for (UserPreference userPreference : preferredSitesRotaplan) {
				if (entity.getId().equals(userPreference.getEntityUuid())) {
					checkBox.setValue(true);
				}
			}

			checkBox.addValueChangeListener(e-> {
				if (checkBox.isChecked()) {
					preferencesService.createPreference(context,
							sitesTable.getSingleSelected().getId(), null);
				} else {
					preferencesService.deletePreferenceByEntity(context,
							sitesTable.getSingleSelected().getId());
				}
			});
			return checkBox;
		});
	}
	
	public void closeWindow(){
		this.close(CLOSE_ACTION_ID,true);
	}
}