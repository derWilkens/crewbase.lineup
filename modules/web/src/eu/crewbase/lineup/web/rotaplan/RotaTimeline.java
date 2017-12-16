package eu.crewbase.lineup.web.rotaplan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowManager.OpenType;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import elemental.json.JsonObject;
import eu.crewbase.lineup.Utils;
import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.FunctionCategory;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.dto.TimelineItem;
import eu.crewbase.lineup.entity.period.AbsencePeriod;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.OperationPeriod;
import eu.crewbase.lineup.entity.period.Period;
import eu.crewbase.lineup.entity.period.PeriodJsonDTO;
import eu.crewbase.lineup.entity.period.ShiftPeriod;
import eu.crewbase.lineup.exception.OperationNotFoundException;
import eu.crewbase.lineup.service.EntityService;
import eu.crewbase.lineup.service.TimelineService;
import eu.crewbase.lineup.service.UserpreferencesService;
import eu.crewbase.lineup.web.toolkit.ui.timelinecomponent.RotaplanComponent;
import eu.crewbase.lineup.web.toolkit.ui.timelinecomponent.RotaplanComponent.RotaplandChangeListener;

public class RotaTimeline extends AbstractWindow {
	@Inject
	private DataManager dataManager;
	@Inject
	private Metadata metadata;
	private RotaplanComponent rotaplan;
	private TimelineDTO dto;

	/* UI-Components */
	@Inject
	private ScrollBoxLayout timelineBox;
	@Inject
	private CheckBox cbxDisplayCampaigns;

	/* Services */
	@Inject
	private TimelineService timelineDTOService;
	@Inject
	private UserpreferencesService preferencesService;
	@Inject
	private EntityService entityService;

	@Inject
	private CollectionDatasource<ShiftPeriod, UUID> shiftPeriodsDs;
	@Inject
	private CollectionDatasource<FunctionCategory, UUID> functionCategoriesDs;
	@Inject
	private CollectionDatasource<Site, UUID> sitesDs;

	@Override
	public void init(Map<String, Object> params) {

		super.init(params);
		shiftPeriodsDs.refresh();
		rotaplan = new RotaplanComponent();
		loadRotaplanDto();
		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		box.addComponent(rotaplan);
		rotaplan.setListener(new InnerListener());

		initCheckBoxDisplayCampaigns();
	}

	private void loadRotaplanDto() {
		dto = timelineDTOService.getRotoplanDto();
		if (dto != null) {
			rotaplan.addDTO("rotaplan", dto);
		}
	}

	private void initCheckBoxDisplayCampaigns() {
		UserPreference preferenceUUID = preferencesService
				.getPreference(UserPreferencesContext.RotaplanDisplayCampaigns, null);
		if (preferenceUUID != null) {
			cbxDisplayCampaigns.setValue(true);
		}
		cbxDisplayCampaigns.addValueChangeListener(event -> {
			if (Boolean.TRUE.equals(event.getValue())) {
				preferencesService.createPreference(UserPreferencesContext.RotaplanDisplayCampaigns, null, "true");

			} else {
				preferencesService.deletePreferenceByEntity(UserPreferencesContext.RotaplanDisplayCampaigns, null);
			}
			loadRotaplanDto();
		});
	}

	public void openDepartmentChooser() {
		Window departmentChoose = openWindow("lineup$DepartmentUser.choose", WindowManager.OpenType.DIALOG);
		departmentChoose.addCloseListener(new CloseListener() {

			@Override
			public void windowClosed(String actionId) {
				loadRotaplanDto();
			}
		});
	}

	public void openSitePeriodChooser() {
		Window departmentChoose = openWindow("lineup$SitePeriod.choose", WindowManager.OpenType.DIALOG);
		departmentChoose.addCloseListener(new CloseListener() {

			@Override
			public void windowClosed(String actionId) {
				loadRotaplanDto();
			}
		});
	}

	class InnerListener implements RotaplandChangeListener {
		boolean isCalledAlready;

		@Override
		public void itemAdded(JsonObject jsonItem) {

			if (isCalledAlready)
				return;
			isCalledAlready = true;

			PeriodJsonDTO parsedItem = parse(jsonItem);// json auslesen, in DTO
														// packen
			Period period = null;

			if (parsedItem.getItemIncomplete()) {
				ShiftPeriodChooser dialog = openTemplateChooser(parsedItem);
				dialog.addCloseListener(new CloseListener() {
					
					@Override
					public void windowClosed(String actionId) {
						isCalledAlready = false;
						
					}
				});
			} else {
				period = createAndSavePeriod(parsedItem);
				addToJsState(period); // dem State hinzufügen
				isCalledAlready = true;
			}

		}

		private Period createPeriod(PeriodJsonDTO parsedItem) {
			Period period = null;
			try {
				period = (Period) metadata.create(Class.forName(parsedItem.getClazzName()));
				period.readDto(parsedItem);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return period;
		}

		private Period createAndSavePeriod(PeriodJsonDTO parsedItem) {
			return dataManager.commit(createPeriod(parsedItem));
		}

		// } catch (
		// catch (OperationNotFoundException e) {
		// showNotification("Der gewählte Zeitraum liegt außerhalb einer
		// Bemannungsphase.");
		// }
		//
		// Exception e) {
		// // TODO Auto-generated catch block
		// //showNotification("Error: " + e.getLocalizedMessage());
		// showNotification("<code>Fehlermeldung</code>",
		// e.getLocalizedMessage(),
		// NotificationType.ERROR_HTML);
		// e.printStackTrace();
		// }

		private void addToJsState(Period item) {
			// nur gespeichert items annehmen
			TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(item, UserPreferencesContext.Rotaplan);
			rotaplan.addTimelineItem(timelineItem);
		}

		private void openPeriodEditor(Period period) {

			openEditor(period, OpenType.DIALOG).addCloseListener(new CloseListener() {

				@Override
				public void windowClosed(String actionId) {
					
					// dataManager.commit(period); offensichtlich wird die
					// Period schon im Editor gespeichert
					TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(period,
							UserPreferencesContext.Rotaplan);
					rotaplan.addTimelineItem(timelineItem);

				}
			});
		}

		private ShiftPeriodChooser openTemplateChooser(PeriodJsonDTO parsedItem) {
			ShiftPeriodChooser dialog = (ShiftPeriodChooser) openWindow("lineup$ShiftPeriod.choose", OpenType.DIALOG);
			dialog.addCloseListener(new CloseListener() {

				@Override
				public void windowClosed(String actionId) {

					parsedItem.setSite(dialog.getSite());
					parsedItem.setDuration(dialog.getDuration());
					parsedItem.setClazzName(dialog.getClazzName());
					parsedItem.setColor(dialog.getColor());
					parsedItem.setRemark(dialog.getRemark());
					if (actionId.equals("OK")) {
						OperationPeriod operationPeriod = null;
						try {
							if(parsedItem.getSite()!=null){
							operationPeriod = timelineDTOService.getOperationPeriod(parsedItem.getSite(),
									parsedItem.getStartDate(), parsedItem.getEndDate());
							parsedItem.setOperationPeriod(operationPeriod);
							}							
							addToJsState(createAndSavePeriod(parsedItem)); // dem State hinzufügen

						} catch (OperationNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (actionId.equals("OPEN_INDIVIDUAL")) {
						openPeriodEditor(createPeriod(parsedItem));
						parsedItem.setItemIncomplete(true);
					}
				}
			});
			return dialog;
		}

		@Override
		public void itemMoved(JsonObject jsonItem) {
			//verschieben testen
			ShiftPeriod newPeriod = null;

			try {
				PeriodJsonDTO parsedItem = parse(jsonItem);
				newPeriod = (ShiftPeriod) metadata.create(Class.forName(jsonItem.getString("clazzName")));
				String className = jsonItem.getString("clazzName");

				if (className.equals(AbsencePeriod.class.getName())) {
					newPeriod = entityService.getById(AbsencePeriod.class, parsedItem.getEntityId());
				}
				if (className.equals(AttendencePeriod.class.getName())) {
					newPeriod = entityService.getById(AttendencePeriod.class, parsedItem.getEntityId());
				}

				newPeriod.readDto(parsedItem);

			} catch (Exception e) {
				e.printStackTrace();
			}

			TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(newPeriod,
					UserPreferencesContext.Rotaplan);

			CommitContext commitContext = new CommitContext(newPeriod);
			dataManager.commit(commitContext);
			// rotaplan.addTimelineItem(timelineItem);

		}

		private ShiftPeriod loadPeriodX(JsonObject jsonItem) {
			ShiftPeriod shiftPeriod = null;
			Class<?> cls = null;
			try {
				cls = Class.forName(jsonItem.getString("clazzName"));
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (cls.equals(AttendencePeriod.class)) {
				shiftPeriod = loadAttendencePeriod(UUID.fromString(jsonItem.getString("id")));
			} else if (cls.equals(AbsencePeriod.class)) {
				shiftPeriod = loadAbsencePeriod(UUID.fromString(jsonItem.getString("id")));
			}
			return shiftPeriod;
		}

		private ShiftPeriod loadAttendencePeriod(UUID uuid) {
			LoadContext<AttendencePeriod> loadContext = LoadContext.create(AttendencePeriod.class).setId(uuid)
					.setView("attendencePeriod-view");
			return dataManager.load(loadContext);
		}

		private ShiftPeriod loadAbsencePeriod(UUID uuid) {
			LoadContext<AbsencePeriod> loadContext = LoadContext.create(AbsencePeriod.class).setId(uuid)
					.setView("absencePeriod-view");
			return dataManager.load(loadContext);
		}

		@Override
		public void editItem(String id) {
			//doppelKlick auf Item sollte Editor öffenen
			
			ShiftPeriod dutyPeriod = shiftPeriodsDs.getItem(UUID.fromString(id));

			if (dutyPeriod != null) {
				Window openEditor = openEditor(dutyPeriod, WindowManager.OpenType.DIALOG);
				openEditor.addCloseListener(new CloseListener() {

					@Override
					public void windowClosed(String actionId) {
						shiftPeriodsDs.refresh();
						ShiftPeriod editedDutyPeriod = shiftPeriodsDs.getItem(UUID.fromString(id));
						TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(editedDutyPeriod,
								UserPreferencesContext.Rotaplan);
						rotaplan.addTimelineItem(timelineItem);
					}
				});
			}
		}

		@Override
		public void itemDeleted(JsonObject jsonItem) {

			try {
				Period newItem = (Period) metadata.create(Class.forName(jsonItem.getString("clazzName")));
				newItem.readDto(parse(jsonItem));

				entityService.remove(newItem);
				rotaplan.removeItem(newItem);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				showNotification("<code>Error notification</code>", "<u>with description</u>",
						NotificationType.ERROR_HTML);
			}

		}

		private PeriodJsonDTO parse(JsonObject jsonItem) {
			PeriodJsonDTO newItem = new PeriodJsonDTO();
			try {
				if (jsonItem.hasKey("id")) {
					newItem.setEntityId(UUID.fromString(jsonItem.getString("id")));
				}
				if (jsonItem.hasKey("start")) {
					newItem.setStartDate(jsonDateToDateWoTime(jsonItem.getString("start")));

				} else {
					newItem.setStartDate(Utils.clearDate(new Date()));
					newItem.setItemIncomplete(true);
				}
				// oder die Duration oder 1
				if (jsonItem.hasKey("end")) {
					newItem.setEndDate(jsonDateToDate(jsonItem.getString("end")));
				}
				if (jsonItem.hasKey("duration") && !jsonItem.getString("duration").equals("null")) {
					newItem.setDuration(Integer.parseInt(jsonItem.getString("duration")));
				}

				if (newItem.getEndDate() == null && newItem.getDuration() > 0) {
					Calendar c = Calendar.getInstance();
					c.setTime(newItem.getStartDate());
					c.add(Calendar.DAY_OF_YEAR, newItem.getDuration());
					newItem.setEndDate(c.getTime());
				}

				if (jsonItem.hasKey("userId")) {
					newItem.setPersonOnDuty(
							entityService.getById(AppUser.class, UUID.fromString(jsonItem.getString("userId"))));
				} else {
					newItem.setItemIncomplete(true);
				}

				if (jsonItem.hasKey("siteId") && !jsonItem.getString("siteId").equals("null")
						&& !jsonItem.getString("siteId").equals("")) {

					UUID siteId = UUID.fromString(jsonItem.getString("siteId"));
					newItem.setSite(entityService.getById(Site.class, siteId));
				}
				if (newItem.getSite() != null) {
					OperationPeriod operationPeriod = timelineDTOService.getOperationPeriod(newItem.getSite(),
							newItem.getStartDate(), newItem.getEndDate());

					newItem.setOperationPeriod(operationPeriod);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getLocalizedMessage());
			}
			return newItem;
		}

		private Date jsonDateToDate(String rawDate) throws ParseException {
			DateFormat formatter = null;
			if (rawDate.length() == 10) {
				formatter = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			}
			return formatter.parse(rawDate);
		}

		private Date jsonDateToDateWoTime(String rawDate) throws ParseException {
			return dateToDateWoTime(jsonDateToDate(rawDate));
		}

		private Date dateToDateWoTime(Date date) throws ParseException {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.parse(formatter.format(date));
		}

		@Override
		public void addSubItem(JsonObject jsonItem) {
			// TODO Auto-generated method stub

		}
	}
}