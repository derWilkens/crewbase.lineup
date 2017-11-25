package eu.crewbase.lineup.gui.screens;

import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import eu.crewbase.lineup.entity.batchimport.PeriodImportStage;
import eu.crewbase.lineup.service.PeriodImportService;

public class Importperiods extends AbstractWindow {

	@Inject
	private TextArea taImport;

	@Inject
	private PeriodImportService importService;

	@Inject
	private CollectionDatasource<PeriodImportStage, UUID> periodImportStagesDs;

	public void onImportRawData(Component source) {

		int result = importService.parseCsv(taImport.getRawValue());

		periodImportStagesDs.clear();
		periodImportStagesDs.refresh();
		
		if (result == 0) {
			showNotification("Keine Zeilen gelesen", NotificationType.ERROR);
		} else if (result == 1) {
			showNotification("1 Zeile gelesen", NotificationType.TRAY);
		} else {
			showNotification(result + " Zeilen gelesen", NotificationType.TRAY);
		}
	}

	public void onCreateCampaigns(Component source) {
		int result = importService.createOrUpdateCampaign();
		showNotification(result + " Einträge wurden erstellt bzw. neu angelegt.", NotificationType.HUMANIZED);
		periodImportStagesDs.refresh();
		taImport.setValue("");
	}

	public void onClearStage(Component source) {
		periodImportStagesDs.clear();
		periodImportStagesDs.commit();
	}

	public void onClearRawDataTextArea(Component source) {
		taImport.setValue("");
	}
}