package eu.crewbase.lineup.gui.screens;

import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import eu.crewbase.lineup.entity.batchimport.PeriodImportStage;
import eu.crewbase.lineup.service.UserImportService;

public class ImportUsers extends AbstractWindow {
	
	@Inject
	private TextArea taImport;

	@Inject
	private UserImportService importService;

	@Inject
	private CollectionDatasource<PeriodImportStage, UUID> userImportStagesDs;


	public void importRawData(Component source){
		int result = importService.parseCsv(taImport.getRawValue());

		userImportStagesDs.clear();
		userImportStagesDs.refresh();
		
		if (result == 0) {
			showNotification("Keine Zeilen gelesen", NotificationType.ERROR);
		} else if (result == 1) {
			showNotification("1 Zeile gelesen", NotificationType.TRAY);
		} else {
			showNotification(result + " Zeilen gelesen", NotificationType.TRAY);
		}
	}
	
	public void createUsers(Component source){
		int result = importService.createOrUpdateUser();
		showNotification(result + " Einträge wurden erstellt bzw. neu angelegt.", NotificationType.HUMANIZED);
		userImportStagesDs.refresh();
		taImport.setValue("");
	}
	
	public void onClearStage(Component source) {
		userImportStagesDs.clear();
		userImportStagesDs.commit();
	}
	
	public void clearRawDataTextArea(Component source){
		taImport.setValue("");
	}
}