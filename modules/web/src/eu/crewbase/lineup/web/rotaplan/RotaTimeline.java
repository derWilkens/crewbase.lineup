package eu.crewbase.lineup.web.rotaplan;

import java.text.ParseException;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

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
import elemental.json.impl.JreJsonNull;
import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.FunctionCategory;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.PeriodDTO;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.dto.TimelineItem;
import eu.crewbase.lineup.entity.period.AbsencePeriod;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.Period;
import eu.crewbase.lineup.entity.period.ShiftPeriod;
import eu.crewbase.lineup.service.EntityService;
import eu.crewbase.lineup.service.RotaplanService;
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
	private RotaplanService rotaplanService;

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

			ShiftPeriodChooser dialog = (ShiftPeriodChooser) openWindow("lineup$ShiftPeriod.choose", OpenType.DIALOG);
			dialog.addCloseListener(new CloseListener() {
				PeriodDTO parsedItem = parse(jsonItem);

				@Override
				public void windowClosed(String actionId) {
					if (!actionId.equals(CLOSE_ACTION_ID)) {
						parsedItem.setSiteId(dialog.getSiteId());
						parsedItem.setDuration(dialog.getDuration());
						parsedItem.setClazzName(dialog.getClazzName());
						parsedItem.setColor(dialog.getColor());
						parsedItem.setRemark(dialog.getRemark());

						try {
							Period period = rotaplanService.createPeriod(parsedItem);

							isCalledAlready = false;
							if (actionId.equals("OK")) {
								addToJsState(period);
							}

							if (actionId.equals("OPEN_INDIVIDUAL")) {
								openPeriodEditor(period);
							}
						} catch (ClassNotFoundException | ParseException e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
				}
			});
		}

		private void addToJsState(Period item) {
			// nur gespeichert items annehmen
			TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(item, UserPreferencesContext.Rotaplan);
			rotaplan.addTimelineItem(timelineItem);
		}

		private void openPeriodEditor(Period period) {

			openEditor(period, OpenType.DIALOG).addCloseListener(new CloseListener() {

				@Override
				public void windowClosed(String actionId) {
					addToJsState(period);
				}
			});
		}

		@Override
		public void itemMoved(JsonObject jsonItem) {
			Period period = rotaplanService.updatePeriod(parse(jsonItem));
			addToJsState(period);

			// //verschieben testen
			// ShiftPeriod newPeriod = null;
			// //umbauen, in das DTO nur die Strings aus dem JSON-Object, dann
			// in Backend-Function die Entitäten befüllen
			// //ist das nicht eigentlich ein TimelineItem was da zurück kommt?
			// try {
			// PeriodJsonDTO parsedItem = parse(jsonItem);
			// newPeriod = (ShiftPeriod)
			// metadata.create(Class.forName(jsonItem.getString("clazzName")));
			// String className = jsonItem.getString("clazzName");
			//
			// if (className.equals(AbsencePeriod.class.getName())) {
			// newPeriod = entityService.getById(AbsencePeriod.class,
			// parsedItem.getEntityId());
			// }
			// if (className.equals(AttendencePeriod.class.getName())) {
			// newPeriod = entityService.getById(AttendencePeriod.class,
			// parsedItem.getEntityId());
			// }
			//
			// newPeriod.readDto(parsedItem);
			//
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			//
			// TimelineItem timelineItem =
			// timelineDTOService.periodToTimelineItem(newPeriod,
			// UserPreferencesContext.Rotaplan);
			//
			// CommitContext commitContext = new CommitContext(newPeriod);
			// dataManager.commit(commitContext);
			// // rotaplan.addTimelineItem(timelineItem);

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
			// doppelKlick auf Item sollte Editor öffenen

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
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		private PeriodDTO parse(JsonObject jsonItem) {
			PeriodDTO newItem = new PeriodDTO();
			
			if (jsonItem.hasKey("clazzName")) {
				newItem.setClazzName(jsonItem.getString("clazzName"));
			}
			if (jsonItem.hasKey("id")) {
				newItem.setEntityId(jsonItem.getString("id"));
			}
			if (jsonItem.hasKey("start")) {
				newItem.setStartDate(jsonItem.getString("start"));

			}
			if (jsonItem.hasKey("end")) {
				newItem.setEndDate(jsonItem.getString("end"));
			}
			if (jsonItem.hasKey("duration") && !(jsonItem.get("duration") instanceof JreJsonNull)) {
				newItem.setDuration(Integer.parseInt(jsonItem.getString("duration")));
			}
			if (jsonItem.hasKey("userId")) {
				newItem.setUserId(jsonItem.getString("userId"));
			}

			if (jsonItem.hasKey("siteId") && !(jsonItem.get("siteId") instanceof JreJsonNull)
					&& !jsonItem.getString("siteId").equals("")) {
				newItem.setSiteId(jsonItem.getString("siteId"));
			}
			return newItem;
		}

		@Override
		public void addSubItem(JsonObject jsonItem) {
			// TODO Auto-generated method stub

		}
	}
}