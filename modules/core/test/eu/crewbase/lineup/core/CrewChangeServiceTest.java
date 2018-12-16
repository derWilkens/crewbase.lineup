package eu.crewbase.lineup.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.wayfare.AnchorWaypoint;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.Ticket;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;
import eu.crewbase.lineup.service.CrewChangeService;
import eu.crewbase.lineup.service.CrewChangeServiceBean;
import eu.crewbase.lineup.service.EntityService;

public class CrewChangeServiceTest extends LineupTestContainer {
	private static final Logger log = LoggerFactory.getLogger(LineupTestContainer.class);
	private Site emdn;
	private Site bwal;
	private Site dwal;

	private CrewChangeService service;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);
		try (Transaction tx = persistence.createTransaction()) {
			List<CrewChange> ccList =  persistence.getEntityManager().createQuery("select cc from lineup$CrewChange cc", CrewChange.class).getResultList();
			for (CrewChange crewChange : ccList) {
				persistence.getEntityManager().remove(crewChange);
			}
			List<Site> siteList = persistence.getEntityManager().createQuery("select s from lineup$Site s", Site.class).getResultList();
			for (Site site : siteList) {
				persistence.getEntityManager().remove(site);
			}
			tx.commit();
		}
		service = AppBeans.get(CrewChangeService.NAME);
		emdn = createSite("Emden", "EMDN", 53.38893, 7.2263314);
		createSite("Hannover", "HANN", 52.3796457, 9.691432);
		createSite("Burgdorf", "BURF", 52.4742371, 9.9413628);
		createSite("Celle", "CELL", 52.6455589, 9.9578078);
		Site pein = createSite("Peine", "PEIN", 52.3161997, 10.1630724);
		createSite("Wunstorf", "WUNF", 52.4313259, 9.31948230);
		log.info("Dist EMDN - PEIN: " +  emdn.getDistanceTo(pein));
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
	public void testReachableSites() {
		UUID ccId = createCC();
		CrewChange cc;
		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);

		}
		List<Site> reachableSites = service.getReachableSites(transfer.getId());

		System.out.println(reachableSites.size());
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
		dto.setOccupiedSeatsWay1(2);
		dto.setOccupiedSeatsWay2(3);

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
			Query query = persistence.getEntityManager()
					.createQuery("select t from lineup$Ticket t where t.transfer.id = :transferId", Ticket.class)
					.setParameter("transferId", transfer.getId());
			List<Ticket> ticketList = query.getResultList();
			assertEquals(ticketList.size(), 5);

		}

	}

}
