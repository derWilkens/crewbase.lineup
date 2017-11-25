package eu.crewbase.lineup.web.department;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.TreeTable;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.Department;
import eu.crewbase.lineup.service.UserpreferencesService;

public class DepartmentUserChoose extends AbstractWindow {
	@Inject
	private ComponentsFactory departmentSelectedComponentsFactory;
	@Inject
	protected TreeTable<Department> depTreeTable;

	@Inject
	private UserpreferencesService preferencesService;

	@Inject
	protected HierarchicalDatasource<Department, UUID> departmentsDs;

	@Override
	public void init(Map<String, Object> params) {


			departmentsDs.refresh();
			List<UserPreference> preferredRotaplanDepartments = preferencesService
					.getPreferences(UserPreferencesContext.RotaplanDepartments);
			depTreeTable.expandAll();

		
		depTreeTable.addGeneratedColumn("selected", entity -> {
			CheckBox checkBox = departmentSelectedComponentsFactory.createComponent(CheckBox.class);

			presetDepartementTreeCheckboxes(preferredRotaplanDepartments, entity, checkBox);
			checkBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChanged(ValueChangeEvent e) {
					e.getPrevValue();
					e.getValue();
					
					if (checkBox.isChecked()) {
						preferencesService.createPreference(UserPreferencesContext.RotaplanDepartments,
								depTreeTable.getSingleSelected().getId(), null);
					} else {
						preferencesService.deletePreferenceByEntity(UserPreferencesContext.RotaplanDepartments,
								depTreeTable.getSingleSelected().getId());
					}
				}
			});
			return checkBox;
		});
	}

	private void presetDepartementTreeCheckboxes(List<UserPreference> preferredRotaplanDepartments, Department entity,
			CheckBox checkBox) {

		for (UserPreference userPreference : preferredRotaplanDepartments) {
			if (entity.getId().equals(userPreference.getEntityUuid())
					&& userPreference.getContextId().equals(UserPreferencesContext.RotaplanDepartments)) {
				checkBox.setValue(true);
			}
		}
	}
	public void closeWindow(){
		this.close(CLOSE_ACTION_ID,true);
	}
	
}