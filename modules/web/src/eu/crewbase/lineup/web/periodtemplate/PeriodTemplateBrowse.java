package eu.crewbase.lineup.web.periodtemplate;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.ColorPicker;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.EntityCombinedScreen;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import eu.crewbase.lineup.entity.period.PeriodTemplate;

public class PeriodTemplateBrowse extends EntityCombinedScreen {
	@Inject
	private ComponentsFactory componentsFactory;

	public Component generateColorField(Datasource<PeriodTemplate> datasource, String property) {
		ColorPicker colorPicker = componentsFactory.createComponent(ColorPicker.class);
		colorPicker.setDatasource(datasource, property);
		colorPicker.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChanged(ValueChangeEvent e) {
				if(e.getValue()!=null){
					datasource.getItem().setColor(e.getValue().toString());
				}
				
			}
		});
		return colorPicker;
	}
}