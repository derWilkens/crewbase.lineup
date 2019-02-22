package eu.crewbase.lineup.web.traveloption;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.filter.Condition;
import com.haulmont.cuba.core.global.filter.ParameterInfo;
import com.haulmont.cuba.core.global.filter.QueryFilter;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Accordion.Tab;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.TravelOptionStatus;
import eu.crewbase.lineup.service.TravelOptionService;

public class TravelOptionList extends AbstractLookup {
	@Inject
	private ComponentsFactory componentsFactory;

	@Inject
	private Table travelOptionsTable;
	@Inject
	private GroupDatasource availableTicketsDs;
	@Inject
	private GroupDatasource approveBookingsDs;
	@Inject
	private GroupDatasource myBookingsDs;
	@Inject
	private GroupDatasource overviewBookingsDs;

	@Inject
	private TravelOptionService travelOptionService;
	
	private String tableDatasourceParam;
	private String windowLabel;

	@Override
	public void init(Map<String, Object> params) {
		// TODO Auto-generated method stub
		super.init(params);
		tableDatasourceParam = (String) params.get("tableDatasource");
		windowLabel = (String)params.get("windowLabel");
		this.setCaption(windowLabel);
		GroupDatasource datasourceParam;
		try {
			datasourceParam = (GroupDatasource) getClass().getDeclaredField(tableDatasourceParam).get(this);
			travelOptionsTable.setDatasource(datasourceParam);
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		
	}
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
		Button bookBtn = null;
		if (entity.getStatus().equals(TravelOptionStatus.Init)) {
			bookBtn = componentsFactory.createComponent(Button.class);
			bookBtn.setAction(this.getAction("openBookingDialog"));
			bookBtn.setCaption("Book >");
			bookBtn.setStyleName("friendly");
		}
		if (entity.getStatus().equals(TravelOptionStatus.Requested) && tableDatasourceParam.equals("approveBookingsDs")) {
			bookBtn = componentsFactory.createComponent(Button.class);
			bookBtn.setAction(this.getAction("openApprovalDialog"));
			bookBtn.setCaption("Accept/Decline >");
			bookBtn.setStyleName("friendly");
		}
		return bookBtn;
	}

	public void onOpenBookingDialog(Component source) {
		TravelOption travelOption = (TravelOption) travelOptionsTable.getSingleSelected();
		TravelOptionBookTransfer editor = (TravelOptionBookTransfer) openEditor("lineup$TravelOption.edit",
				travelOption, WindowManager.OpenType.DIALOG);

		editor.addCloseListener(new CloseListener() {

			@Override
			public void windowClosed(String actionId) {
				travelOptionsTable.getDatasource().refresh();

			}
		});
	}

    public void onOpenApprovalDialog(Component source) {
    	TravelOption travelOption = (TravelOption) travelOptionsTable.getSingleSelected();
    	 showOptionDialog(
                 "Accept or Reject",
                 "Confirm the booking of the requested number of seats:",
                 MessageType.CONFIRMATION.width("500px"),
                 new Action[]{
                		 new BaseAction("approveRequest") {
                             @Override
                             public void actionPerform(Component component) {
                            	 travelOptionService.approveBooking(travelOption.getId());
                            	 travelOptionsTable.getDatasource().refresh();
                             }

                             @Override
                             public String getCaption() {
                                 return "Accept";
                             }
                         },
                		 new BaseAction("declineRequest") {
                             @Override
                             public void actionPerform(Component component) {
                            	 travelOptionService.declineBooking(travelOption.getId());
                            	 travelOptionsTable.getDatasource().refresh();
                             }

                             @Override
                             public String getCaption() {
                                 return "Decline";
                             }
                         },
                         new DialogAction(DialogAction.Type.CLOSE)
                 }
         );
    }
}