package eu.crewbase.lineup.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;

import eu.crewbase.lineup.DateFormatter;
import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Department;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.PeriodDTO;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.OperationPeriod;
import eu.crewbase.lineup.entity.period.Period;
import eu.crewbase.lineup.service.RotaplanService;
import eu.crewbase.lineup.service.TimelineService;
import eu.crewbase.lineup.service.UserpreferencesService;

public class RotplanServiceTest extends LineupTestContainer {
	
	private UserpreferencesService preferenceService;
	private RotaplanService rotaplanService;
	private TimelineService timelineService;
	private Site site;
	private List<AttendencePeriod> periodList;

	private Logger log = LoggerFactory.getLogger(RotplanServiceTest.class);

	private Department department;
	private AppUser personOnDuty;
	// private OperationPeriod op;

	private AttendencePeriod createdPeriod;

	private Period opsPeriod;

	private AppUser person1;

	private AppUser person2;

	@Before
	public void setUp() throws Exception {
		dataManager = AppBeans.get(DataManager.class);
		timelineService = AppBeans.get(TimelineService.NAME);
		preferenceService = AppBeans.get(UserpreferencesService.NAME);
		rotaplanService = AppBeans.get(RotaplanService.NAME);
		persistence = cont.persistence();
		metadata = cont.metadata();

		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);

		// department = metadata.create(Department.class);
		// department.setClient(1);
		// department.setAcronym("AB");
		// dataManager.commit(department);
		//
		// preferenceService.createPreference(UserPreferencesContext.RotaplanDepartments,
		// department.getUuid(), null);
		//
		personOnDuty = metadata.create(AppUser.class);
		personOnDuty.setLastname("Lastname");
		personOnDuty.setFirstname("Firstname");
		personOnDuty.setDepartment(department);
		
		person1 = dataManager.commit(personOnDuty);
		person2 = dataManager.commit(personOnDuty);
		//

		site = metadata.create(Site.class);
		site.setSiteName("Testsite");
		site.setItemDesignation("TEST");
		dataManager.commit(site);
		//
		// UserPreference prefEmlView =
		// preferenceService.createPreference(UserPreferencesContext.EmlDisplaySite,
		// site.getUuid(),
		// null);
		//
		// op = metadata.create(OperationPeriod.class);
		//
		// op.setClient(1);
		// op.setSite(site);
		// op.setStartDate(new Date());
		// op.setEndDate(new Date());
		// dataManager.commit(op);
		//
		// periodList = new ArrayList<AttendencePeriod>();
		//
		// AttendencePeriod p = metadata.create(AttendencePeriod.class);
		// p.setOperationPeriod(op);
		// p.setClient(1);
		// p.setStartDate(new Date());
		// p.setEndDate(new Date());
		// p.getOperationPeriod().setSite(site);
		// p.setPersonOnDuty(personOnDuty);
		// dataManager.commit(p);
		//
		// periodList.add(p);
		//
		// DataManager dataManager = AppBeans.get(DataManager.class);
		//
		// // die evtl. vorhanden UserPref für EML display aufräumen
		// LoadContext<UserPreference> loadContext =
		// LoadContext.create(UserPreference.class)
		// .setQuery(LoadContext.createQuery("select p from
		// lineup$UserPreference p where p.contextId = :context")
		// .setParameter("context", UserPreferencesContext.EmlDisplaySite))
		// .setView("userPreference-view");
		//// List<UserPreference> upList = dataManager.loadList(loadContext);
		//// log.info("UPref vorhanden:" + upList.size());
		////
		//// for (UserPreference userPreference : upList) {
		//// if
		// (userPreference.getContextId().equals(UserPreferencesContext.EmlDisplaySite))
		// {
		//// dataManager.remove(userPreference);
		//// log.info("Remove UserPref: " + userPreference.getEntityUuid());
		//// }
		//// }
		////
		//// UserPreference pref = metadata.create(UserPreference.class);
		//// pref.setClient(1);
		//// pref.setContextId(UserPreferencesContext.EmlDisplaySite);
		//// pref.setEntityUuid(site.getId());
		////
		//// // Save the customer to the database
		//// dataManager.commit(pref);
		//
		// /*
		// * Transactions DataManager always starts a new transaction and
		// commits
		// * it on operation completion, thus returning entities in the detached
		// * state.
		// */

	}

	@After
	public void tearDown() throws Exception {
		try (Transaction tx = persistence.createTransaction()) {
		// for (AttendencePeriod period : periodList) {
		// dataManager.remove(period);
		// }
		// dataManager.remove(op);
		
//		persistence.getEntityManager().remove(site);
//		persistence.getEntityManager().remove(person1);
//		persistence.getEntityManager().remove(person2);
//		persistence.getEntityManager().remove(opsPeriod);
		}
	}

	@Test
	public void testCreatePeriod() {
		try {
			PeriodDTO opsDto = new PeriodDTO();
			opsDto.setClazzName(OperationPeriod.class.getName());
			opsDto.setDuration(10);
			opsDto.setSiteId(site.getId().toString());
			opsDto.setStartDate("2017-12-31");
			opsPeriod = rotaplanService.createPeriod(opsDto);

			PeriodDTO dto = new PeriodDTO();
			dto.setClazzName(AttendencePeriod.class.getName());
			dto.setDuration(1);
			dto.setRemark("UnittestRemark");
			dto.setSiteId(site.getId().toString());
			dto.setUserId(personOnDuty.getId().toString());
			dto.setStartDate("2017-12-31");

			createdPeriod = (AttendencePeriod) rotaplanService.createPeriod(dto);
			dto.setEntityId(createdPeriod.getId().toString());
			assertTrue(createdPeriod.getStartDate() != null);
			assertTrue(createdPeriod.getEndDate() != null);

			dto.setStartDate("2018-01-01");
			dto.setEndDate("2018-01-02");
			dto.setUserId(person2.getId().toString());
			createdPeriod = (AttendencePeriod) rotaplanService.updatePeriod(dto);
			assertTrue(createdPeriod != null);
			assertEquals("01.01.2018", DateFormatter.toDDMMJJJJ(createdPeriod.getStartDate()));
			assertEquals("02.01.2018", DateFormatter.toDDMMJJJJ(createdPeriod.getEndDate()));
			assertEquals(person2.getId(), createdPeriod.getPersonOnDuty().getId());

			rotaplanService.deletePeriod(createdPeriod.getId().toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
