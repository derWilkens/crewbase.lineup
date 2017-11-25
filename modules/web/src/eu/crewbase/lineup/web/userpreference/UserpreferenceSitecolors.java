package eu.crewbase.lineup.web.userpreference;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.ColorPicker;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.vaadin.shared.ui.colorpicker.Color;

import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.service.UserpreferencesService;

public class UserpreferenceSitecolors extends AbstractWindow {
	@Inject
	private UserpreferencesService userpreferenceService;

	@Inject
	private ComponentsFactory siteColorsComponentsFactory;

	@Inject
	private Table<Site> siteColors;
	@Inject
	private CollectionDatasource<UserPreference, UUID> userPreferencesDs;
	@Inject
	private UserpreferencesService preferencesService;
	
	@Override
	public void init(Map<String, Object> params) {
		initSiteUserSettings();
		initSiteBackgroundColor();
	}

	private void initSiteBackgroundColor(){
		siteColors.addGeneratedColumn("backgroundColor", entity -> {
			ColorPicker colorPicker = siteColorsComponentsFactory.createComponent(ColorPicker.class);
			List<UserPreference> preferredBackgroundColors = preferencesService
					.getPreferences(UserPreferencesContext.SiteBackgroundColor);
			
			for (UserPreference userPreference : preferredBackgroundColors) {
				if (entity.getId().equals(userPreference.getEntityUuid())) {
					Color siteColor = new Color(Integer.parseInt(userPreference.getUserValue(), 16));
					colorPicker.setValue(siteColor);
				}
			}
			
			colorPicker.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChanged(ValueChangeEvent e) {
					if (colorPicker.getValue() != null) {
							preferencesService.createPreference(UserPreferencesContext.SiteBackgroundColor,
								siteColors.getSingleSelected().getId(), colorPicker.getValue());
					}
				};
			});
			
			return colorPicker;
		});
	}
	private void initSiteUserSettings() {
		siteColors.addGeneratedColumn("selected", entity -> {
			ColorPicker colorPicker = siteColorsComponentsFactory.createComponent(ColorPicker.class);
			userPreferencesDs.refresh();
			for (UserPreference userPreference : userPreferencesDs.getItems()) {
				if (entity.getId().equals(userPreference.getEntityUuid())
						&& userPreference.getContextId().equals(UserPreferencesContext.SiteColor)) {
					Color siteColor = new Color(Integer.parseInt(userPreference.getUserValue(), 16));
					colorPicker.setValue(siteColor);
				}
			}

			colorPicker.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChanged(ValueChangeEvent e) {

					if (colorPicker.getValue() != null) {
						UserPreference preference = userpreferenceService.getPreference(
								UserPreferencesContext.SiteColor, siteColors.getSingleSelected().getId());
						if (preference != null) {

						} else {
							final DataSupplier dataservice = userPreferencesDs.getDataSupplier();
							final UserPreference userPreference = dataservice
									.newInstance(userPreferencesDs.getMetaClass());
							userPreference.setContextId(UserPreferencesContext.SiteColor);
							userPreference.setEntityUuid((UUID) siteColors.getSingleSelected().getId());
							userPreference.setUserValue(colorPicker.getValue());
							userPreferencesDs.addItem((UserPreference) userPreference);
							userPreferencesDs.commit();
						}
					}
				}

			});
			return colorPicker;
		});
	}
}