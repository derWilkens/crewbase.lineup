package eu.crewbase.lineup.web.traveloption;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LookupField;

import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.service.TravelOptionService;

import com.haulmont.cuba.gui.components.Component;

public class TravelOptionBookTransfer extends AbstractEditor<TravelOption> {

	@Inject
	private LookupField bookedSeats;
	@Inject
	private Label favTrip;
	@Inject
	private Label transferDate;
	@Inject
	private Label takeOff;
	@Inject
	private TravelOptionService travelOptionService;

	@Override
	public void postInit() {
		initComponents(getItem());
	}

	private void initComponents(TravelOption travelOption) {
		List<Integer> list = new ArrayList<>();

		for (int i = 0; i <= travelOption.getAvailableSeats(); i++) {
			list.add(i);
		}
		bookedSeats.setOptionsList(list);
		favTrip.setValue(travelOption.getFavoriteTrip().getStartSite().getSiteName() + " - "
				+ travelOption.getFavoriteTrip().getDestination().getSiteName());

		SimpleDateFormat fligthDate = new SimpleDateFormat("yyyy-MM-dd");
		String transferDateStr = fligthDate.format(travelOption.getTransfer().getCrewChange().getStartDate());
		transferDate.setValue(transferDateStr);

		SimpleDateFormat takeOffTime = new SimpleDateFormat("hh:mm");
		String takeOffStr = takeOffTime.format(travelOption.getTransfer().getWaypoints().get(0).getTakeOff());
		takeOff.setValue(takeOffStr);

	}

    public void bookSeats(Component source) {
    	travelOptionService.bookSeats(getItem().getId(), (Integer) bookedSeats.getValue());
    	this.close(WINDOW_CLOSE);
    }

    public void onCancel(Component source) {
        this.close(WINDOW_CLOSE);
    }
}