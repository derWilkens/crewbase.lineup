package eu.crewbase.lineup.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.wayfare.AnchorWaypoint;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;
import eu.crewbase.lineup.service.CrewChangeService;
import eu.crewbase.lineup.service.EntityService;

public class CrewChangeServiceTest extends LineupTestContainer {
	private Site emdn;
	private Site bwal;
	private Site dwal;

	private CrewChangeService service;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);

		service = AppBeans.get(CrewChangeService.NAME);
		emdn = createSite("Emden", "EMDN", 53.38893, 7.2263314);
		// createSite("alpha ventus (EDYV)",54.0000 ,6.6269);
		// createSite("Amrumbank West (EDYA)",54.5219 ,7.7456);
		// createSite("Bard 1",54.3528 ,6.0000);
		// createSite("Borkum Riffgrund 1 (EDYB)",53.9736 ,6.5592);
		// createSite("Borkum Riffgrund 2",53.9617 ,6.4744);
		bwal = createSite("BorWin alpha", "BWAL", 54.3569, 6.0169);
		// createSite("BorWin B",54.3586 ,6.0169);
		// createSite("Butendiek (EKBU)",55.0214 ,7.7964);
		// createSite("DanTysk (EKDT)",55.1267 ,7.2372);
		dwal = createSite("DolWin alpha", "DWAL", 54.0061, 6.4236);
		// createSite("DolWin beta (EDYD)",53.9858 ,6.9319);
		// createSite("Fino 1 (EDYF)",54.0239 ,6.5931);
		// createSite("Fino 3",55.2028 ,7.1525);
		// createSite("Gemini (Buitengaats)",54.0392 ,6.0339);
		// System.out.println(bwal.getDistanceTo(dwal));

	}

	private Site createSite(String name, String itemDesignation, double lat, double lon) {
		Site site1 = metadata.create(Site.class);
		site1.setClient(1);
		site1.setSiteName(name);
		site1.setItemDesignation(itemDesignation);
		site1.setLatitude(lat);
		site1.setLongitude(lon);
		dataManager.commit(site1);
		return site1;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateCrewChange() {

		UUID ccId = createCC();
		validateCrewChange(ccId);
	}

	@Test
	public void testAddWaypoint() {
		UUID ccId = createCC();
		CrewChange cc;
		Transfer transfer;

		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);

		}
		// EMDN - BWAL + DWAL
		service.addWaypoint(transfer.getUuid(), dwal.getUuid(), transfer.getAnchorWaypoint().getNextWaypoint().getId());

		try (Transaction tx = persistence.createTransaction()) {
			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDN - BWAL - DWAL - EMDN", transfer.getRoute());
		}
	}

	@Test
	public void testremoveWaypoint() {
		UUID ccId = createCC();
		CrewChange cc;
		Transfer transfer;
		AnchorWaypoint awp1;

		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);

		}
		service.addWaypoint(transfer.getUuid(), dwal.getUuid(), transfer.getAnchorWaypoint().getNextWaypoint().getId());

		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDN - BWAL - DWAL - EMDN", transfer.getRoute());
		}

		service.removeWaypoint(transfer.getAnchorWaypoint().getNextWaypoint().getId());

		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDN - DWAL - EMDN", transfer.getRoute());
		}

	}

	@Test
	public void testMoveWaypoint() {

		UUID ccId = createCC();
		int distance = 0;
		CrewChange cc;
		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDN - BWAL - EMDN", transfer.getRoute());
		}
		
		// erstmal einen WP hinzufügen
		// EMDN - BWAL + DWAL
		service.addWaypoint(transfer.getUuid(), dwal.getUuid(), transfer.getAnchorWaypoint().getNextWaypoint().getId());

		// neu laden
		AnchorWaypoint awp1;
		Waypoint wpBwal;
		Waypoint wpDwal;

		try (Transaction tx = persistence.createTransaction()) {
			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDN - BWAL - DWAL - EMDN", transfer.getRoute());
			
			awp1 = transfer.getAnchorWaypoint();
			wpBwal = awp1.getNextWaypoint();
			assertEquals(wpBwal.getSite().getItemDesignation(), "BWAL");
			wpDwal = wpBwal.getNextWaypoint();
			assertEquals(wpDwal.getSite().getItemDesignation(), "DWAL");
			distance = transfer.getTotalDistance();
		}
		service.moveWaypoint(wpDwal.getId(), awp1.getId());

		try (Transaction tx = persistence.createTransaction()) {
			// neu laden
			transfer = persistence.getEntityManager().find(Transfer.class, transfer.getId());
			assertEquals("EMDN - DWAL - BWAL - EMDN", transfer.getRoute());
		}
	}

	private UUID createCC() {
		CrewChangeCreateDTO dto = metadata.create(CrewChangeCreateDTO.class);
		dto.setStartDateTime(new Date());
		dto.setDepartureSite(emdn);
		dto.setArrivalSite(bwal);
		dto.setHelicopterType(null);
		dto.setOccupiedSeatsWay1(1);
		dto.setOccupiedSeatsWay2(2);

		return service.createCrewChange(dto);

	}

	private void validateCrewChange(UUID ccId) {
		try (Transaction tx = persistence.createTransaction()) {

			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			assertTrue(cc.getTransfers().size() == 1);

			Transfer transfer = cc.getTransfers().get(0);

			AnchorWaypoint awp1 = transfer.getAnchorWaypoint();
			assertEquals(awp1.getSite().getItemDesignation(), "EMDN");

			Waypoint wp1 = awp1.getNextWaypoint();
			assertNotNull(wp1);
			assertEquals(wp1.getSite().getItemDesignation(), "BWAL");
			assertNull(wp1.getNextWaypoint());
			assertEquals(wp1.getPreviousStandstill().getSite().getItemDesignation(), "EMDN");

			assertTrue(transfer.getTotalDistance() > 100);
			assertEquals("EMDN - BWAL - EMDN", transfer.getRoute());

		}

	}

}
