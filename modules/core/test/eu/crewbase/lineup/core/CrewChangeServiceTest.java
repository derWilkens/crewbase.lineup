package eu.crewbase.lineup.core;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.service.CrewChangeService;

public class CrewChangeServiceTest extends LineupTestContainer {
	private Site site1; 
	private CrewChangeService service;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);
		
		service = AppBeans.get(CrewChangeService.NAME);
		
		site1 = metadata.create(Site.class);
		site1.setClient(1);
		site1.setSiteName("Testsite");
		site1.setItemDesignation("TEST");
		dataManager.commit(site1);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateCrewChange() {
		CrewChangeCreateDTO dto = metadata.create(CrewChangeCreateDTO.class);
		dto.setCrewChangeDate(new Date());
		dto.setArrivalSite(site1);
		dto.setDepartureSite(site1);
		dto.setHelicopterType(null);
		dto.setOccupiedSeatsWay1(1);
		dto.setOccupiedSeatsWay2(2);
		dto.setTakeOff(new Date());
		
		service.createCrewChange(dto);
		
	}

}
