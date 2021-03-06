package eu.crewbase.lineup.web.rotaplan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowManager.OpenType;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import elemental.json.JsonObject;
import eu.crewbase.lineup.Utils;
import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.FunctionCategory;
import eu.crewbase.lineup.entity.coredata.OffshoreUser;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TimelineConfig;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.dto.TimelineItem;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.service.TimelineService;
import eu.crewbase.lineup.service.UserpreferencesService;
import eu.crewbase.lineup.web.toolkit.ui.timelinecomponent.RotaplanComponent;
import eu.crewbase.lineup.web.toolkit.ui.timelinecomponent.RotaplanComponent.RotaplandChangeListener;

public class EmlTimeline extends AbstractWindow {

	@Inject
	private DataManager dataManager;

	private RotaplanComponent rotaplan;
	private TimelineDTO dto;

	/* UI-Components */
	@Inject
	private ScrollBoxLayout timelineBox;
	@Inject
	private OptionsGroup siteChooser;

	/* Services */
	@Inject
	private TimelineService timelineDTOService;
	@Inject
	private UserpreferencesService preferencesService;

	/* Datasources */
	@Inject
	private Datasource<AttendencePeriod> attendencePeriodDs;
	@Inject
	private CollectionDatasource<AttendencePeriod, UUID> attendencePeriodsDs;
	@Inject
	private CollectionDatasource<FunctionCategory, UUID> functionCategoriesDs;
	@Inject
	private CollectionDatasource<Site, UUID> sitesDs;

	private Site displayedSite = null;

	@Override
	public void init(Map<String, Object> params) {

		super.init(params);
		attendencePeriodsDs.refresh();
		rotaplan = new RotaplanComponent();

		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		box.addComponent(rotaplan);
		rotaplan.setListener(new InnerListener());
		initSiteChooser();
		if (displayedSite != null) {
			loadEmlDto();
		}
	}

	private void loadEmlDto() {
		//dto = timelineDTOService.getEmlDto();
		if (dto != null) {
			rotaplan.addDTO("rotaplan", dto);
		}
	}

	private void initSiteChooser() {
		List<Site> preferredSites = preferencesService.getSites(UserPreferencesContext.SiteEml);
		List<UserPreference> displayedSitePreference = preferencesService
				.getPreferences(UserPreferencesContext.EmlDisplaySite);

		if (!displayedSitePreference.isEmpty()) {
			try {
				displayedSite = preferredSites.stream().filter(
						s -> displayedSitePreference.get(0).getEntityUuid().toString().equals(s.getUuid().toString()))
						.findFirst().get();
			} catch (Exception e1) {
				// die displayed Site ist nicht bei den prefered Sites, macht
				// aber nix. Wir machen weiter
				e1.printStackTrace();
			}
		}

		Map<String, UUID> map = new LinkedHashMap<>();
		for (Site site : preferredSites) {
			map.put(site.getItemDesignation(), site.getId());
		}
		siteChooser.setOptionsMap(map);
		if (displayedSite != null) {
			siteChooser.setValue(displayedSite.getId());
		}
		siteChooser.addValueChangeListener(e -> {

				preferencesService.deletePreferenceByEntity(UserPreferencesContext.EmlDisplaySite, null);
				preferencesService.createPreference(UserPreferencesContext.EmlDisplaySite, (UUID) e, null);
				loadEmlDto();
		});
	}

	public TimelineConfig getDutyPeriodGroupedByUserConfig() {
		TimelineConfig dutyPeriodConfig = new TimelineConfig();
		dutyPeriodConfig.setGroupIdFunction((AttendencePeriod e) -> e.getPersonOnDuty().getUuid().toString());
		dutyPeriodConfig.setGroupLabelFunction((AttendencePeriod e) -> e.getPersonOnDuty().getInstanceName());
		dutyPeriodConfig.setParentGroupIdFunction((AttendencePeriod e) -> null);
		dutyPeriodConfig.setItemLabelFunction((AttendencePeriod e) -> {
			String result = "";
			if (e.getOperationPeriod().getSite() != null) {
				result = e.getOperationPeriod().getSite().getItemDesignation();
			}
//			if (e.getFunctionCategory() != null) {
//				result = result + " " + e.getFunctionCategory().getCategoryName();
//			}
			return result;
		});
		dutyPeriodConfig.setStyleFunction((AttendencePeriod e) -> {
			if (null != e.getOperationPeriod().getSite()) {
				String colorHex = preferencesService.getSiteColorPreference(e.getOperationPeriod().getSite().getUuid());
				if (colorHex != null) {
					return "background-color: #" + colorHex + ";";
				}
			}
			return "";
		});
		dutyPeriodConfig.setEditableFunction((AttendencePeriod e) -> {
			return true;
		});
		dutyPeriodConfig.setTypeFunction((AttendencePeriod e) -> {
			return "range";
		});
		return dutyPeriodConfig;
	}

	class InnerListener implements RotaplandChangeListener {
		boolean isCalledAlready;

		@Override
		public void itemAdded(JsonObject jsonItem) {
			// hier belassen
			// wie wird im Backend aus einem DetachedObject ein AttachedObject?
			// Wenn es unvollständig ist, muss der editor geöffnet werden und es
			// darf vorher nicht gespeichert sein
			// das TimelineItem muss auch zurückgegeben werden

			if (isCalledAlready)
				return;
			isCalledAlready = true;
			boolean itemIncomplete = false;

			// Neues Objekt erzeugen im dem DS hinzufügen
			DataSupplier dataservice = attendencePeriodDs.getDataSupplier();
			AttendencePeriod newItem = dataservice.newInstance(attendencePeriodDs.getMetaClass());

			// Datum
			try {
				if (jsonItem.hasKey("start")) {
					newItem.setStartDate(jsonDateToDateWoTime(jsonItem.getString("start")));
				} else {
					newItem.setStartDate(Utils.clearDate(new Date()));
					itemIncomplete = true;
				}

				int duration = 1; // entweder das gelieferte Endedatum nehmen,
									// oder die Duration oder 1
				// if (jsonItem.hasKey("end")) {
				// newItem.setEnd(jsonDateToDate(jsonItem.getString("end")));
				// }
				// else
				if (jsonItem.hasKey("duration") && !jsonItem.getString("duration").equals("null")) {
					duration = Integer.parseInt(jsonItem.getString("duration"));
				}

				Calendar c = Calendar.getInstance();
				c.setTime(newItem.getStartDate());
				c.add(Calendar.DAY_OF_YEAR, duration);
				newItem.setEndDate(c.getTime());

			} catch (Exception e) {
				e.printStackTrace();
				itemIncomplete = true;
			}

			// Person über dataManager laden
			if (jsonItem.hasKey("userId")) {
				newItem.setPersonOnDuty(getUserById(jsonItem.getString("userId")));
			} else {
				itemIncomplete = true;
			}

			// Site über dataManager laden
			if (jsonItem.hasKey("siteId") && !jsonItem.getString("siteId").equals("null")) {
				sitesDs.refresh();
				newItem.getOperationPeriod().setSite(sitesDs.getItem(UUID.fromString(jsonItem.getString("siteId"))));
			} else {
				// itemIncomplete = true; nicht jeder Dienst braucht eine Site,
				// wenn doch kann manuell nachgepflegt werden.
			}

			// FunctionCategory über dataManager laden
			if (jsonItem.hasKey("functionCategoryId")) {
				functionCategoriesDs.refresh();
//				newItem.setFunctionCategory(
//						functionCategoriesDs.getItem(UUID.fromString(jsonItem.getString("functionCategoryId"))));
			} else {
				itemIncomplete = true;
			}

			if (itemIncomplete) {
				openEditor(newItem, OpenType.DIALOG).addCloseListener(new CloseListener() {

					@Override
					public void windowClosed(String actionId) {
						TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(newItem,
								UserPreferencesContext.Rotaplan);
						rotaplan.addTimelineItem(timelineItem);
						isCalledAlready = false;
					}
				});
			} else {

				attendencePeriodDs.setItem(newItem);
				attendencePeriodsDs.updateItem(newItem);
				getDsContext().commit();
				TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(newItem,
						UserPreferencesContext.Rotaplan);
				rotaplan.addTimelineItem(timelineItem);
				isCalledAlready = false;
			}
		}

		private OffshoreUser getUserById(String id) {
			LoadContext<OffshoreUser> loadContext = LoadContext.create(OffshoreUser.class).setId(UUID.fromString(id))
					.setView("offshoreUser-browser-view");
			return dataManager.load(loadContext);
		}

		@Override
		public void itemMoved(JsonObject jsonItem) {
			attendencePeriodsDs.refresh();
			AttendencePeriod dutyPeriod = attendencePeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));
			if (jsonItem.hasKey("group")) {
				// wenn die Person geändert wurde
				if (!dutyPeriod.getPersonOnDuty().getId().toString().equals(jsonItem.getString("group"))) {
					dutyPeriod.setPersonOnDuty(getUserById(jsonItem.getString("group")));
				}
			}
			try {
				dutyPeriod.setStartDate(jsonDateToDate(jsonItem.getString("start")));
				dutyPeriod.setEndDate(jsonDateToDate(jsonItem.getString("end")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// getDsContext().commit();//den Context vorher committen, sonst
			// wird beim schließen gefragt.
			attendencePeriodDs.setItem(dutyPeriod);
			// dutyPeriodDs.commit();
			getDsContext().commit();
			dutyPeriod = attendencePeriodsDs.getItem(dutyPeriod.getId());
			TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(dutyPeriod,
					UserPreferencesContext.Rotaplan);
			rotaplan.addTimelineItem(timelineItem);
		}

		@Override
		public void editItem(String id) {

			AttendencePeriod dutyPeriod = attendencePeriodsDs.getItem(UUID.fromString(id));

			if (dutyPeriod != null) {
				Window openEditor = openEditor(dutyPeriod, WindowManager.OpenType.DIALOG);
				openEditor.addCloseListener(new CloseListener() {

					@Override
					public void windowClosed(String actionId) {
						attendencePeriodsDs.refresh();
						AttendencePeriod editedDutyPeriod = attendencePeriodsDs.getItem(UUID.fromString(id));
						TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(editedDutyPeriod,
								UserPreferencesContext.Rotaplan);
						rotaplan.addTimelineItem(timelineItem);
					}
				});
			}
		}

		@Override
		public void itemDeleted(JsonObject jsonItem) {
			attendencePeriodsDs.refresh();
			AttendencePeriod dutyPeriod = attendencePeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));
			if (dutyPeriod != null) {
				attendencePeriodsDs.removeItem(dutyPeriod);
				getDsContext().commit();
				// Das Item ist im Frontend schon entfernt worden. Aber was ist,
				// wenn das programmatisch gemacht wird?
				rotaplan.removeItem(dutyPeriod);
			}
		}

		private Date jsonDateToDate(String rawDate) throws ParseException {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
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