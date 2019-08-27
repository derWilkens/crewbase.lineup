
package eu.crewbase.lineup.web.campaign;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import eu.crewbase.lineup.web.toolkit.ui.timelinecomponent.TimelineComponent;
import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.FunctionCategory;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.period.Period;
import eu.crewbase.lineup.service.TimelineService;

public class CampaignOverview extends AbstractLookup {

	/**
	 * Alle UserPreferences des aktuell angemeldeten Benutzers
	 */
	@Inject
	private CollectionDatasource<UserPreference, UUID> userPreferencesDs;

	/**
	 * FunctionCategories werden in der Tabelle functionCategoryUserSettings
	 * angezeigt. Selektierte FunctionCategories werden in die Preferences
	 * geschrieben
	 */
	// @Inject
	// private CollectionDatasource<Period, UUID> periodTypesDs;

	@Inject
	private Table<FunctionCategory> functionCategoryUserSettings;

	@Inject
	private Table<User> personsOnDutyUserSettings;
	/**
	 * Sites werden in Tabelle angezeigt Selektierte Sites werden in die
	 * Preferences geschrieben
	 */
	// @Inject
	// private CollectionDatasource<Site, UUID> sitesDs;

	@Inject
	private Table<Site> siteUserSettings;

	/*
	 * Wo kommen die denn her?
	 */
	@Inject
	private ComponentsFactory typeSelectedComponentsFactory;

	@Inject
	private ComponentsFactory siteSelectedComponentsFactory;

	/**
	 * Alle Kampagnen zur Anzeige in der Timeline, werden anschließend nach
	 * User-Präferenzen gefilter
	 */
	@Inject
	private CollectionDatasource<Period, UUID> campaignsDs;

	/**
	 * Alle ServicePeriods zur Anzeige in der Timeline, werden anschließend nach
	 * User-Präferenzen gefilter
	 */
	@Inject
	private CollectionDatasource<Period, UUID> dutyPeriodsDs;

	@Inject
	private VBoxLayout timelineBox;

	private TimelineComponent timeline;

	@Inject
	private TimelineService timelineDTOService;
	private TimelineDTO dto;

	@Override
	public void init(Map<String, Object> params) {

		//userPreferencesDs.refresh();

		campaignsDs.refresh();

		dutyPeriodsDs.refresh();

		initSiteUserSettings();
		initTypeUserSettings();
		initPersonsUserSettings();

		// JS-UI-Komponente
		timeline = new TimelineComponent();
		dto = timelineDTOService.getDto(UserPreferencesContext.CampaignBrowse);
		if (dto != null) {
			timeline.addDTO(UserPreferencesContext.CampaignBrowse, dto);
			timeline.refresh();
		}

		timeline.setStart("2017-03-01");//@todo: heute - 30 Tage, Ende heute + 90 Tage
		timeline.setEnd("2017-05-01");

		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);

		box.addComponent(timeline);

	}

	private void initTypeUserSettings() {
		functionCategoryUserSettings.addGeneratedColumn("selected", entity -> {

			CheckBox checkBox = typeSelectedComponentsFactory.createComponent(CheckBox.class);

			for (UserPreference userPreference : userPreferencesDs.getItems()) {
				if (entity.getId().equals(userPreference.getEntityUuid())
						&& userPreference.getContextId().equals(UserPreferencesContext.CampaignBrowse)) {
					checkBox.setValue(true);
				}
			}
			checkBox.addValueChangeListener(e->{

				if (checkBox.isChecked()) {
					final DataSupplier dataservice = userPreferencesDs.getDataSupplier();

					final UserPreference newItem = dataservice.newInstance(userPreferencesDs.getMetaClass());
					newItem.setContextId(UserPreferencesContext.CampaignBrowse);
					newItem.setEntityUuid(functionCategoryUserSettings.getSingleSelected().getId());
					userPreferencesDs.addItem(newItem);
					userPreferencesDs.commit();
				} else {
					UserPreference tmp = null;
					Iterator<UserPreference> iter = userPreferencesDs.getItems().iterator();
					while (iter.hasNext()) {
						UserPreference userPreference = iter.next();
						if (userPreference.getEntityUuid()
								.equals(functionCategoryUserSettings.getSingleSelected().getId())) {
							tmp = userPreference;
						}
					}
					userPreferencesDs.removeItem(tmp);
					userPreferencesDs.commit();
				}
				timeline.addDTO(UserPreferencesContext.CampaignBrowse, timelineDTOService.getDto(UserPreferencesContext.CampaignBrowse));
				timeline.refresh();
			});
			return checkBox;
		});

	}

	private void initSiteUserSettings() {
		siteUserSettings.addGeneratedColumn("selected", entity -> {
			CheckBox checkBox = siteSelectedComponentsFactory.createComponent(CheckBox.class);
			for (UserPreference userPreference : userPreferencesDs.getItems()) {
				if (entity.getId().equals(userPreference.getEntityUuid())
						&& userPreference.getContextId().equals(UserPreferencesContext.CampaignBrowse)) {
					checkBox.setValue(true);
				}
			}

			checkBox.addValueChangeListener(e-> {

				if (checkBox.isChecked()) {
					final DataSupplier dataservice = userPreferencesDs.getDataSupplier();
					final UserPreference newItem = dataservice.newInstance(userPreferencesDs.getMetaClass());
					newItem.setContextId(UserPreferencesContext.CampaignBrowse);
					newItem.setEntityUuid(siteUserSettings.getSingleSelected().getId());
					userPreferencesDs.addItem(newItem);
					userPreferencesDs.commit();
				} else {
					UserPreference tmp = null;
					Iterator<UserPreference> iter = userPreferencesDs.getItems().iterator();
					while (iter.hasNext()) {
						UserPreference userPreference = iter.next();
						try {
							if (userPreference.getEntityUuid().equals(siteUserSettings.getSingleSelected().getId())) {
								tmp = userPreference;
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					userPreferencesDs.removeItem(tmp);
					userPreferencesDs.commit();
				}
				timeline.addDTO(UserPreferencesContext.CampaignBrowse, timelineDTOService.getDto(UserPreferencesContext.CampaignBrowse));
				timeline.refresh();
			});
			return checkBox;
		});
	}

	private void initPersonsUserSettings() {
		personsOnDutyUserSettings.addGeneratedColumn("selected", entity -> {
			CheckBox checkBox = siteSelectedComponentsFactory.createComponent(CheckBox.class);
			for (UserPreference userPreference : userPreferencesDs.getItems()) {
				if (entity.getId().equals(userPreference.getEntityUuid())
						&& userPreference.getContextId().equals(UserPreferencesContext.CampaignBrowse)) {
					checkBox.setValue(true);
				}
			}

			checkBox.addValueChangeListener(e->{

				if (checkBox.isChecked()) {
					final DataSupplier dataservice = userPreferencesDs.getDataSupplier();
					final UserPreference newItem = dataservice.newInstance(userPreferencesDs.getMetaClass());
					newItem.setContextId(UserPreferencesContext.CampaignBrowse);
					newItem.setEntityUuid(personsOnDutyUserSettings.getSingleSelected().getId());
					userPreferencesDs.addItem(newItem);
					userPreferencesDs.commit();
				} else {
					UserPreference tmp = null;
					Iterator<UserPreference> iter = userPreferencesDs.getItems().iterator();
					while (iter.hasNext()) {
						UserPreference userPreference = iter.next();
						if (userPreference.getEntityUuid().equals(personsOnDutyUserSettings.getSingleSelected().getId())) {
							tmp = userPreference;
						}
					}
					userPreferencesDs.removeItem(tmp);
					userPreferencesDs.commit();
				}
				timeline.addDTO(UserPreferencesContext.CampaignBrowse, timelineDTOService.getDto(UserPreferencesContext.CampaignBrowse));
				timeline.refresh();
			});
			return checkBox;
		});
	}

	public void save() {
	}

	public void cancel() {
	}
}