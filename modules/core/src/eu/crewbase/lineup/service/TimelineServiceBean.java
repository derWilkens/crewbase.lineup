package eu.crewbase.lineup.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.cap.coredata.Role;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Department;
import eu.crewbase.lineup.entity.coredata.FunctionCategory;
import eu.crewbase.lineup.entity.coredata.NumberRangeRule;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.coredata.SiteRoleRule;
import eu.crewbase.lineup.entity.dto.DutyPeriodDTO;
import eu.crewbase.lineup.entity.dto.TimelineConfig;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.dto.TimelineGroup;
import eu.crewbase.lineup.entity.dto.TimelineItem;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.MaintenanceCampaign;
import eu.crewbase.lineup.entity.period.Period;
import eu.crewbase.lineup.entity.period.PeriodTemplate;
import eu.crewbase.lineup.entity.period.ShiftPeriod;
import eu.crewbase.lineup.entity.period.SitePeriod;

@Service(TimelineService.NAME)
public class TimelineServiceBean extends PreferencesService implements TimelineService {

	@Inject
	private Persistence persistence;

	@Inject
	private UserpreferencesService userPreferenceSerivce;


	@Override
	public TimelineDTO getDto(UserPreferencesContext context) {

		TimelineDTO dto = new TimelineDTO();

		TimelineConfig campaignTimelineConfig = getSitePeriodConfig(); 

		TimelineConfig dutyPeriodConfig = getAttendencePeriodGroupedBySiteConfig();

		try (Transaction tx = persistence.createTransaction()) {
			// preferences des Users für den context laden
			List<UserPreference> userPreferenceList = getUserPreferences(persistence.getEntityManager(), context);

			// weil das im Context der Kampagnenübersicht geladen wird
			// gucken wir, welche Entitäten wir denn brauchen
			// also erstmal die Typen laden, die in den Preferences sind

			List<Site> preferredSites = getPreferredSites(persistence.getEntityManager(), context);
			List<FunctionCategory> preferredFunctionCategories = loadPreferredFunctionCategories(userPreferenceList);
//			Set<PeriodSubClass> preferredSubClassList = new HashSet<PeriodSubClass>();
//
//			for (FunctionCategory functionCategory : preferredFunctionCategories) {
//				if (functionCategory.getPeriodSubClass() != null) {
//					preferredSubClassList.add(functionCategory.getPeriodSubClass());
//				}
//			}
//
//			for (PeriodSubClass periodSubClass : preferredSubClassList) {
//
//				if (periodSubClass.equals(PeriodSubClass.ModeOfOperation)) {
//					dto.addItems(getCampaigns(preferredSites, preferredFunctionCategories), campaignTimelineConfig);
//
//				} else if (periodSubClass.equals(PeriodSubClass.Administration)) {
//					List<AppUser> personsOnDuty = loadPreferredPersonsOnDuty(userPreferenceList);
//					List<AttendencePeriod> dutyPeriods = getDutyPeriods(personsOnDuty, preferredSites,
//							preferredFunctionCategories);
//					dto.addItems(dutyPeriods, dutyPeriodConfig);
//				}
//			}

			tx.commit();
		}
		return dto;

	}

	@Override
	public TimelineDTO getRotoplanDto() {

		TimelineDTO dto = new TimelineDTO();
		TimelineConfig rotaplanConfig = getAttendencePeriodGroupedByUserConfig();

		try (Transaction tx = persistence.createTransaction()) {

			// Alle Teams mit Members holen
			dto.getGroupList().addAll(getTimelineGroups());

			// Periods der preferred User holen
			List<AppUser> preferredPersons = getPersonsByPreferredDepartment(persistence.getEntityManager(),
					UserPreferencesContext.RotaplanDepartments);
			List<ShiftPeriod> dutyPeriods = getDutyPeriods(preferredPersons, null, null);
			dto.addItems(dutyPeriods, rotaplanConfig);

			//Kampagnen als Hintergrund anzeigen?
			if (!getUserPreferences(persistence.getEntityManager(), UserPreferencesContext.RotaplanDisplayCampaigns)
					.isEmpty()) {
				// Campaigns werden anhand der preferred Sites und preferred
				// FunctionCategories vom SubClassTyp Campaign(!) geholt
				List<UserPreference> preferredRotaplanFunctionCategories = getUserPreferences(
						persistence.getEntityManager(), UserPreferencesContext.Rotaplan);
				List<Site> preferredSites = getPreferredSites(persistence.getEntityManager(),
						UserPreferencesContext.Rotaplan);
				List<FunctionCategory> preferredFunctionCategories = loadPreferredFunctionCategories(
						preferredRotaplanFunctionCategories);
//				Set<PeriodSubClass> preferredSubClassList = new HashSet<PeriodSubClass>();
//
//				for (FunctionCategory functionCategory : preferredFunctionCategories) {
//					if (functionCategory.getPeriodSubClass() != null) {
//						preferredSubClassList.add(functionCategory.getPeriodSubClass());
//					}
//				}
				dto.addItems(getCampaigns(preferredSites, preferredFunctionCategories),
						getSitePeriodAsBackgroundConfig());
			}

			// preferred Sites holen, hierfür die Drag-Buttons angezeigt
			dto.setPeriodTemplates(getPeriodTemplatesDTO());

			tx.commit();
		}
		return dto;

	}

	private List<DutyPeriodDTO> getPeriodTemplatesDTO() {
		List<DutyPeriodDTO> dutyPeriodTemplates = new ArrayList<DutyPeriodDTO>();
		for (PeriodTemplate template : getPeriodTemplates()) {
			DutyPeriodDTO templateDTO = new DutyPeriodDTO();
			templateDTO.setPersonId(template.getUser().getId().toString());
			if (template.getSite() != null) {
				templateDTO.setSiteId(template.getSite().getId().toString());
				templateDTO.setItemDesignation(template.getSite().getItemDesignation());
				String colorHex = userPreferenceSerivce.getSiteColorPreference(template.getSite().getUuid());
				if (colorHex != null) {
					templateDTO.setColor("#" + colorHex);
				}
			}
			templateDTO.setFunctionCategoryId(template.getFunctionCategory().getId().toString());
			templateDTO.setCategoryName(template.getFunctionCategory().getCategoryName());
			templateDTO.setDuration(template.getDefaultDuration());
			dutyPeriodTemplates.add(templateDTO);
		}
		return dutyPeriodTemplates;
	}

	private List<TimelineGroup> getTimelineGroups() {
		List<TimelineGroup> groups = new ArrayList<TimelineGroup>();
		
		// Alle preferred Departments holen
		List<Department> preferredDepartments = getPreferredDepartments(persistence.getEntityManager(),
				UserPreferencesContext.RotaplanDepartments);
		for (Department department : preferredDepartments) {
			TimelineGroup group = new TimelineGroup(department.getUuid().toString(), department.getAcronym(),
					department.getAcronym());
			group.setSubgroupOrder("content");
			Comparator<AppUser> byLastName = Comparator.comparing(u -> u.getLastname()
			// OffshoreUser::getLastName);
			);
			List<AppUser> members = department.getMembers().stream().sorted(byLastName)
					.collect(Collectors.toList());
			int counter = 0;
			for (AppUser user : members) {
				// der parentGroup eine nested hinzufügen
				group.addSubgroup(user.getUuid().toString());
				
				// NestedGroup einzeln erzeugen und auch noch der Liste
				// hinzufügen

				TimelineGroup subGroup = new TimelineGroup(user.getUuid().toString(), user.getInstanceName(),
						String.valueOf(counter));
				subGroup.setShowNestedGroups(false);
				groups.add(subGroup);
				// counter++;
			}
			groups.add(group);
		}
		return groups;
	}

	private List<PeriodTemplate> getPeriodTemplates() {

		String queryString = "select e from lineup$PeriodTemplate e where e.user.id = :userId";

		TypedQuery<PeriodTemplate> query = persistence.getEntityManager().createQuery(queryString,
				PeriodTemplate.class);

		query.setParameter("userId", AppBeans.get(UserSessionSource.class).getUserSession().getUser().getId());
		query.addViewName("dutyPeriodTemplate-view");
		return query.getResultList();
	}

	private TimelineConfig getAttendencePeriodGroupedBySiteConfig() {

		TimelineConfig dutyPeriodConfig = new TimelineConfig();

		dutyPeriodConfig.setGroupIdFunction((AttendencePeriod e) -> e.getOperationPeriod().getSite().getSiteName());
//		dutyPeriodConfig.setParentGroupIdFunction((DutyPeriod e) -> {
//			if (e.getSite().getParentSite() != null) {
//				return e.getSite().getParentSite().getSiteName();
//			} else
//				return null;
//		});
		dutyPeriodConfig.setItemLabelFunction((AttendencePeriod e) -> {

				return e.getPersonOnDuty().getInstanceName();
			
		});
		dutyPeriodConfig.setStyleFunction((AttendencePeriod e) -> {
			String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getOperationPeriod().getSite().getUuid());
			if (colorHex != null) {
				return "background-color: #" + colorHex + ";";
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

	private TimelineConfig getAttendencePeriodGroupedByUserConfig() {
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
//				result = result + " - " + e.getFunctionCategory().getCategoryName();
//			}
			if (e.getRemark() != null) {
				result = result + " - " + e.getRemark();
			}
			return result;
		});
		dutyPeriodConfig.setStyleFunction((AttendencePeriod e) -> {
			if (null != e.getOperationPeriod().getSite()) {
				String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getOperationPeriod().getSite().getUuid());
				if (colorHex != null) {
					Color c = Color.decode("0x"+colorHex);
					String rgb =  c.getRed() + "," + c.getGreen() + "," + c.getBlue();
					return "background-color: rgba("+rgb+", 0.6);";
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

	private TimelineConfig getSitePeriodAsBackgroundConfig() {
		TimelineConfig dutyPeriodConfig = new TimelineConfig();
		dutyPeriodConfig.setGroupIdFunction((SitePeriod e) -> null);
		dutyPeriodConfig.setGroupLabelFunction((SitePeriod e) -> null);
		dutyPeriodConfig.setParentGroupIdFunction((SitePeriod e) -> null);
		dutyPeriodConfig.setItemLabelFunction((SitePeriod e) -> {
			return e.getSite().getItemDesignation() + " " + e.getInstanceName();
		});
		dutyPeriodConfig.setStyleFunction((SitePeriod e) -> {
			if (null != e.getSite()) {
				String colorHex = userPreferenceSerivce.getSiteBackgroundColorPreferrence(e.getSite().getUuid());
				if (colorHex != null) {
					Color c = Color.decode("0x"+colorHex);
					String rgb =  c.getRed() + "," + c.getGreen() + "," + c.getBlue();
					return "background-color: rgba("+rgb+", 0.4);";
				}
					//return "background-color: #" + Integer.toHexString(((Integer.decode("0x"+colorHex) & 0x7f7f7f << 1))) + ";"; 
			}
			return "";
		});
		dutyPeriodConfig.setEditableFunction((SitePeriod e) -> {
			return false;
		});
		dutyPeriodConfig.setTypeFunction((SitePeriod e) -> {
			return "background";
		});
		return dutyPeriodConfig;
	}

	private TimelineConfig getSitePeriodConfig() {
		if (true)
			return null;
		TimelineConfig campaignTimelineConfig = new TimelineConfig();
		campaignTimelineConfig.setGroupIdFunction((SitePeriod e) -> {
			return e.getSite().getSiteName();
		});
		campaignTimelineConfig.setParentGroupIdFunction((SitePeriod e) -> {
			if (e.getSite().getParentSite() != null) {
				return e.getSite().getParentSite().getSiteName();
			} else
				return null;
		});
		campaignTimelineConfig.setItemLabelFunction((SitePeriod e) -> {
			return e.getInstanceName();
		});
		campaignTimelineConfig.setStyleFunction((SitePeriod e) -> {
			String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getSite().getUuid());
			if (colorHex != null) {
				String rgb = String.valueOf(Color.decode("0x"+colorHex).getRGB());
				return "background-color: rgba("+rgb+", 0.6);";
			}
			return "";
		});
		campaignTimelineConfig.setTypeFunction((AttendencePeriod e) -> {
			return "range";
		});
		return campaignTimelineConfig;
	}

	// drei Kriterien: (implizit Type), Site, ServiceUser;
	private List<ShiftPeriod> getDutyPeriods(List<AppUser> personOnDutyList, List<Site> siteList,
			List<FunctionCategory> preferredFunctionCategories) {
		List<ShiftPeriod> dutyPeriods;

		String queryString;
		String queryConcatenator = "where ";

		queryString = "select e from lineup$AttendencePeriod e ";

		if (personOnDutyList != null && personOnDutyList.size() > 0) {
			queryString = queryString + queryConcatenator + "e.personOnDuty.id IN :personsIdList ";
			queryConcatenator = "AND ";
		}
		if (siteList != null && siteList.size() > 0) {
			queryString = queryString + queryConcatenator + "e.site.id IN :siteIdList ";
			queryConcatenator = "AND ";
		}
		if (preferredFunctionCategories != null && preferredFunctionCategories.size() > 0) {
			queryString = queryString + queryConcatenator + "e.functionCategory.id IN :catIdList ";
			queryConcatenator = "AND ";
		}

		TypedQuery<ShiftPeriod> query = persistence.getEntityManager().createQuery(queryString, ShiftPeriod.class);
		if (personOnDutyList != null && personOnDutyList.size() > 0) {
			query.setParameter("personsIdList", getUUIDList(personOnDutyList));
		}
		if (siteList != null && siteList.size() > 0) {
			query.setParameter("siteIdList", getUUIDList(siteList));
		}
		if (preferredFunctionCategories != null && preferredFunctionCategories.size() > 0) {
			query.setParameter("catIdList", getUUIDList(preferredFunctionCategories));
		}

		dutyPeriods = query.getResultList();

		return dutyPeriods;
	}

	private List<MaintenanceCampaign> getCampaigns(List<Site> siteList, List<FunctionCategory> preferredFunctionCategories) {
		List<MaintenanceCampaign> campaigns;

		// String queryString = "select e from lineup$Campaign e where
		// e.site.id in :idList AND e.functionCategory.id in :catIdList";
		String queryString = "select e from lineup$SitePeriod e where e.site.id in :idList";

		TypedQuery<MaintenanceCampaign> query = persistence.getEntityManager().createQuery(queryString, MaintenanceCampaign.class);

		query.setParameter("idList", getUUIDList(siteList));
		// query.setParameter("catIdList",
		// getUUIDList(preferredFunctionCategories));
		campaigns = query.getResultList();

		return campaigns;
	}

	/**
	 * @param userPreferenceList
	 *            UUIDs der Typen die als Präferencen gesetzt wurden
	 * @return List<PeriodType> Liste aller PeriodTypes (Campaing oder
	 *         DutyPeriod usw)
	 * 
	 */
	private List<FunctionCategory> loadPreferredFunctionCategories(List<UserPreference> userPreferenceList) {

		List<UUID> functionCategoryIds = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from lineup$FunctionCategory e where e.id in :entityUUIDs";

		TypedQuery<FunctionCategory> query = persistence.getEntityManager().createQuery(queryString,
				FunctionCategory.class);

		query.setParameter("entityUUIDs", functionCategoryIds);

		return query.getResultList();
	}

	private List<AppUser> loadPreferredPersonsOnDuty(List<UserPreference> userPreferenceList) {
		List<AppUser> entityList;

		List<UUID> entityUUIDs = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from lineup$OffshoreUser e where e.id in :entityUUIDs order by e.lastname asc";

		TypedQuery<AppUser> query = persistence.getEntityManager().createQuery(queryString, AppUser.class);

		query.setParameter("entityUUIDs", entityUUIDs);

		entityList = query.getResultList();

		return entityList;
	}

	@Override
	public Collection<AppUser> getPersonsOnDuty() {

		Collection<AppUser> userList;

		try (Transaction tx = persistence.createTransaction()) {

			String queryString = "select e.personOnDuty from lineup$DutyPeriod e ";

			TypedQuery<AppUser> query = persistence.getEntityManager().createQuery(queryString,
					AppUser.class);

			userList = query.getResultList();

			tx.commit();
		}
		return userList;
	}

	@Override
	public TimelineItem periodToTimelineItem(Period period, UserPreferencesContext context) {
		TimelineConfig timelineConfig = null;
		TimelineItem item = null;
		if (context.equals(UserPreferencesContext.CampaignBrowse)) {
			timelineConfig = getSitePeriodConfig();

		} else if (context.equals(UserPreferencesContext.Rotaplan)) {
			timelineConfig = getAttendencePeriodGroupedByUserConfig();
		}

		try (Transaction tx = persistence.createTransaction()) {
			period = persistence.getEntityManager().find(AttendencePeriod.class, period.getId()); // war
			// mal
			// merge,
			// aber
			// warum?
			item = new TimelineItem(period, timelineConfig);
			tx.commit();
		}
		return item;
	}

	/*
	 * für den definierten Zeitraum in der Zukunft werden sämtliche Dutyperiods ausgewertet.
	 * Daraus ergibt sich tagesscharf die POB 
	 * Map mit Datum - Anzahl
	 * 
	 */
	private List<Role> getRoleDutyPeriodsForSite(UUID siteId) {
		
		Collection<Role> userList;
		int pob = 0;
		try (Transaction tx = persistence.createTransaction()) {
			Site tmpSite = null;
			//über aller SitRoleRules 
			for (SiteRoleRule rule : tmpSite.getSiteRoleRules()) {
				int requiredNumberOfRoles;
				for (NumberRangeRule rangeRule: rule.getRangeRule()) {
					if(pob > rangeRule.getAmountFrom() && pob <= rangeRule.getAmountTo()){
						requiredNumberOfRoles = rangeRule.getRequiredNumber();
					}
				} 
				rule.getRole();
			}
			String queryString = "select e.roles from lineup$Site e ";

			TypedQuery<AppUser> query = persistence.getEntityManager().createQuery(queryString,
					AppUser.class);

			// userList = query.getResultList();

			tx.commit();
		}
		return null;
	}
	
	private int getPobForDate(UUID siteId, Date date) {
		//select period where siteId, periodType = "Anwesend" and start <= date and end <= date
		return persistence.getEntityManager()
		.createQuery("SELECT e from lineup$DutyPeriod e where e.site.id = :siteId and e.functionCategory.periodSubClass = :periodType")
		.setParameter("siteId", siteId)
		//.setParameter("periodType", PeriodSubClass.DutyPeriod)
		.getResultList().size();
	}
	
	@SuppressWarnings("unchecked")
	public AttendencePeriod getLastPeriod(UUID siteId){
		
		return (AttendencePeriod)persistence.getEntityManager()
		.createNativeQuery("SELECT e from lineup$AttendencePeriod e where e.site.id = :siteId")
		.setParameter("siteId", siteId)
		.getResultList()
		.stream().max(Comparator.comparing(AttendencePeriod::getEnd)).get();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AttendencePeriod> getCommingPeriods(UUID siteId) {
		return (List<AttendencePeriod>)persistence.getEntityManager()
		.createQuery("SELECT e from lineup$AttendencePeriod e where e.site.id = :siteId")
		.setParameter("siteId", siteId)
		.getResultList();
	}


}