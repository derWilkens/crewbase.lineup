package eu.crewbase.lineup.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UserSessionSource;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.UserPreference;
import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Department;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.TimelineDTO;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.OperationPeriod;
import eu.crewbase.lineup.service.TimelineService;
import eu.crewbase.lineup.service.UserpreferencesService;



public class TimelineDTOServiceTest extends LineupTestContainer {
	private DataManager dataManager;
	private Persistence persistence;
	private UserpreferencesService preferenceService;
	private Site site;
	private List<AttendencePeriod> periodList;

	private Logger log = LoggerFactory.getLogger(TimelineDTOServiceTest.class);

	private Department department;
	private AppUser personOnDuty;
	private OperationPeriod op;

	@Before
	public void setUp() throws Exception {
		dataManager = AppBeans.get(DataManager.class);
		service = AppBeans.get(TimelineService.NAME);
		preferenceService = AppBeans.get(UserpreferencesService.NAME);
		persistence = cont.persistence();
		metadata = cont.metadata();

		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);

		department = metadata.create(Department.class);
		department.setClient(1);
		department.setAcronym("AB");
		dataManager.commit(department);

		preferenceService.createPreference(UserPreferencesContext.RotaplanDepartments, department.getUuid(), null);
		
		personOnDuty = metadata.create(AppUser.class);
		personOnDuty.setLastname("Lastname");
		personOnDuty.setFirstname("Firstname");
		personOnDuty.setDepartment(department);
		dataManager.commit(personOnDuty);

		site = metadata.create(Site.class);
		site.setClient(1);
		site.setSiteName("Testsite");
		site.setItemDesignation("TEST");
		dataManager.commit(site);

		UserPreference prefEmlView = preferenceService.createPreference(UserPreferencesContext.EmlDisplaySite, site.getUuid(),
				null);

		op = metadata.create(OperationPeriod.class);

		op.setClient(1);
		op.setSite(site);
		op.setStartDate(new Date());
		op.setEndDate(new Date());
		dataManager.commit(op);

		periodList = new ArrayList<AttendencePeriod>();

		AttendencePeriod p = metadata.create(AttendencePeriod.class);
		p.setOperationPeriod(op);
		p.setClient(1);
		p.setStartDate(new Date());
		p.setEndDate(new Date());
		p.getOperationPeriod().setSite(site);
		p.setPersonOnDuty(personOnDuty);
		dataManager.commit(p);

		periodList.add(p);

		DataManager dataManager = AppBeans.get(DataManager.class);

		// die evtl. vorhanden UserPref für EML display aufräumen
		LoadContext<UserPreference> loadContext = LoadContext.create(UserPreference.class)
				.setQuery(LoadContext.createQuery("select p from lineup$UserPreference p where p.contextId = :context")
						.setParameter("context", UserPreferencesContext.EmlDisplaySite))
				.setView("userPreference-view");
//		List<UserPreference> upList = dataManager.loadList(loadContext);
//		log.info("UPref vorhanden:" + upList.size());
//
//		for (UserPreference userPreference : upList) {
//			if (userPreference.getContextId().equals(UserPreferencesContext.EmlDisplaySite)) {
//				dataManager.remove(userPreference);
//				log.info("Remove UserPref: " + userPreference.getEntityUuid());
//			}
//		}
//
//		UserPreference pref = metadata.create(UserPreference.class);
//		pref.setClient(1);
//		pref.setContextId(UserPreferencesContext.EmlDisplaySite);
//		pref.setEntityUuid(site.getId());
//
//		// Save the customer to the database
//		dataManager.commit(pref);

		/*
		 * Transactions DataManager always starts a new transaction and commits
		 * it on operation completion, thus returning entities in the detached
		 * state.
		 */

	}

	@After
	public void tearDown() throws Exception {

		for (AttendencePeriod period : periodList) {
			dataManager.remove(period);
		}
		dataManager.remove(op);
		dataManager.remove(site);
		dataManager.remove(personOnDuty);
		dataManager.remove(department);
	}

	@Test
	public void testGetDto() {
		// fail("Not yet implemented");
		//try (Transaction tx = persistence.createTransaction()) {
			UserpreferencesService prefService = AppBeans.get(UserpreferencesService.NAME);
			prefService.getPreference(UserPreferencesContext.EmlDisplaySite, null);
			// service.getEmlDto();
			TimelineDTO rotoplanDto = service.getRotoplanDto();
			assertTrue( rotoplanDto.getGroupList().size()>0);
			assertEquals("Lastname, Firstname", rotoplanDto.getGroupList().get(0).getContent());
			//tx.commit();
		
	}

}
