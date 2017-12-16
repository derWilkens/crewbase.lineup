package eu.crewbase.lineup.web.rotaplan;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.DataGrid.ButtonRenderer;
import com.haulmont.cuba.gui.components.DataGrid.ColumnGenerator;
import com.haulmont.cuba.gui.components.DataGrid.ColumnGeneratorEvent;
import com.haulmont.cuba.gui.components.DataGrid.RendererClickEvent;
import com.haulmont.cuba.gui.components.DataGrid.RendererClickListener;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import eu.crewbase.lineup.entity.coredata.PeriodKind;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.period.PeriodTemplate;
import eu.crewbase.lineup.service.RotaplanService;

public class ShiftPeriodChooser extends AbstractWindow {

	@Inject
	public RotaplanService service;
	private Site site;
	private int duration;
	private String clazzName;
	private String color;
	private String remark;

	@Inject
	private DataGrid<PeriodTemplate> dataGrid;
	@Inject
	private CollectionDatasource<PeriodTemplate, UUID> periodTemplatesDs;

	public void init(Map<String, Object> params) {

		initFirstColumn();
		initRenderers();
		initIndividualColumn();

		dataGrid.setFocusable(false);

	}

	private void initFirstColumn() {
		
	    DataGrid.Column column = dataGrid.addGeneratedColumn("periodKind",new DataGrid.ColumnGenerator<PeriodTemplate, String>(){
	        @Override
	        public String getValue(DataGrid.ColumnGeneratorEvent<PeriodTemplate> event){
	            return (event.getItem().getPeriodKind() == PeriodKind.Attendence)?event.getItem().getSite().getSiteName():event.getItem().getRemark();
	        }

	        @Override
	        public Class<String> getType(){
	            return String.class;
	        }
	    },1);
	    column.setCaption("");

	}

	private void initIndividualColumn() {
		RendererClickListener listener = new RendererClickListener() {

			@Override
			public void onClick(RendererClickEvent event) {
				event.getColumnId();
				PeriodTemplate template = periodTemplatesDs.getItem((UUID) event.getItemId());
				setSite(template.getSite());
				setClazzName(template.getPeriodKind().getId());

				close("OPEN_INDIVIDUAL", true);
			}
		};

		ColumnGenerator<PeriodTemplate, String> columnGenerator = new DataGrid.ColumnGenerator<PeriodTemplate, String>() {

			@Override
			public String getValue(ColumnGeneratorEvent<PeriodTemplate> event) {
				return "Individual";
			}

			@Override
			public Class<String> getType() {
				return String.class;
			}

		};

		DataGrid.Column column = dataGrid.addGeneratedColumn("individual", columnGenerator);
		column.setCaption("");
		ButtonRenderer btnRenderer = dataGrid.createRenderer(DataGrid.ButtonRenderer.class);
		btnRenderer.setRendererClickListener(listener);
		column.setRenderer(btnRenderer);

	}

	private void initRenderers() {

		RendererClickListener listener = new RendererClickListener() {

			@Override
			public void onClick(RendererClickEvent event) {
				event.getColumnId();
				PeriodTemplate template = periodTemplatesDs.getItem((UUID) event.getItemId());
				Integer value = (Integer) template.getValue(event.getColumnId());
				setSite(template.getSite());
				setDuration(value);
				setColor(template.getColor());
				setRemark(template.getRemark());
				setClazzName(template.getPeriodKind().getId());

				close("OK", true);
			}
		};
		ColumnGenerator<PeriodTemplate, String> columnGenerator = new DataGrid.ColumnGenerator<PeriodTemplate, String>() {

			@Override
			public String getValue(ColumnGeneratorEvent<PeriodTemplate> event) {
				String label = "";
				PeriodTemplate template = event.getItem();
				Integer value = (Integer) template.getValue(event.getColumnId());
				if (value != null) {
					label = value.toString() + (value == 1 ? " Tag" : " Tage");
				}
				return label;
			}

			@Override
			public Class<String> getType() {
				return String.class;
			}

		};

		for (int i = 1; i <= 3; i++) {
			DataGrid.Column column = dataGrid.addGeneratedColumn("duration" + i, columnGenerator);
			column.setCaption("");
			ButtonRenderer btnRenderer = dataGrid.createRenderer(DataGrid.ButtonRenderer.class);
			btnRenderer.setRendererClickListener(listener);
			column.setRenderer(btnRenderer);
		}
		// individual button hinzu, wie wird dann auf close reagiert? anderes
		// close event feuern und weiteren listener bauen
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}