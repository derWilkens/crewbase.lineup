package eu.crewbase.lineup.web.traveloption;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import eu.crewbase.lineup.entity.wayfare.TravelOption;

public class TravelOptionList extends AbstractLookup {
	@Inject
	private ComponentsFactory componentsFactory;

	@Inject
	private Table travelOptionsTable;
	@Inject
	private GroupDatasource travelOptionsDs;

	public Component generateTransferDateCell(TravelOption entity) {
		Label textField = componentsFactory.createComponent(Label.class);
		SimpleDateFormat fligthDate = new SimpleDateFormat("yyyy-MM-dd");
		String transferDate = fligthDate.format(entity.getTransfer().getCrewChange().getStartDate());
		textField.setValue(transferDate);
		return textField;
	}

	public Component generateTransferTakeOffCell(TravelOption entity) {
		Label textField = componentsFactory.createComponent(Label.class);
		SimpleDateFormat takeOffTime = new SimpleDateFormat("hh:mm");
		String takeOff = takeOffTime.format(entity.getTransfer().getWaypoints().get(0).getTakeOff());
		textField.setValue(takeOff);
		return textField;
	}

	public Component generateFavoriteTripCell(TravelOption entity) {
		Label textField = componentsFactory.createComponent(Label.class);
		textField.setValue(entity.getFavoriteTrip().getStartSite().getSiteName() + " - "
				+ entity.getFavoriteTrip().getDestination().getSiteName());
		return textField;
	}

	public Component generateBookTransferCell(TravelOption entity) {
		Button bookBtn = componentsFactory.createComponent(Button.class);
		bookBtn.setAction(this.getAction("openBookingDialog"));
		bookBtn.setCaption("Book");
		// bookBtn.setIcon("SHOPPING_CART");
		return bookBtn;
	}

	public void onOpenBookingDialog(Component source) {
		TravelOption travelOption = (TravelOption) travelOptionsTable.getSingleSelected();
		TravelOptionBookTransfer editor = (TravelOptionBookTransfer) openEditor("lineup$TravelOption.edit", travelOption,
				WindowManager.OpenType.DIALOG);
		
		editor.addCloseListener(new CloseListener() {

			@Override
			public void windowClosed(String actionId) {
				travelOptionsDs.refresh();

			}
		});
	}
}