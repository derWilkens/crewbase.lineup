package eu.crewbase.lineup.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.dto.TripDTO;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Ticket;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.TravelOptionStatus;
import eu.crewbase.lineup.entity.wayfare.Waypoint;
import eu.crewbase.lineup.service.CrewChangeService;
import eu.crewbase.lineup.service.TravelOptionService;

public class CrewChangeServiceTest extends LineupTestContainer {
	private static final Logger log = LoggerFactory.getLogger(LineupTestContainer.class);
	private Site emde;
	private Site bwbe;
	private Site dwal;
	private Site dwbe;
	private CrewChange cc;
	private UUID ccId;
	private CrewChangeService service;
	private TravelOptionService travelOptionService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);

		try (Transaction tx = persistence.createTransaction()) {

			List<CrewChange> ccList = persistence.getEntityManager()
					.createQuery("select cc from lineup$CrewChange cc where cc.createdBy = 'test_admin'",
							CrewChange.class)
					.getResultList();
			for (CrewChange crewChange : ccList) {
				persistence.getEntityManager().remove(crewChange);
			}

			List<Site> siteList = persistence.getEntityManager()
					.createQuery("select s from lineup$Site s where s.createdBy = 'test_admin'", Site.class)
					.getResultList();
			for (Site site : siteList) {
				persistence.getEntityManager().remove(site);
			}
			// deleteRecord(siteList.stream().toArray());
			List<FavoriteTrip> favList = persistence.getEntityManager()
					.createQuery("select s from lineup$FavoriteTrip s where s.createdBy = 'test_admin'",
							FavoriteTrip.class)
					.getResultList();
			for (FavoriteTrip fav : favList) {
				persistence.getEntityManager().remove(fav);
			}
			tx.commit();
		}

		service = AppBeans.get(CrewChangeService.NAME);
		travelOptionService = AppBeans.get(TravelOptionService.NAME);
		emde = getSiteByItemDesignation("EMDE");
		// createSite("Hannover", "HANN", 52.3796457, 9.691432);
		// createSite("Burgdorf", "BURF", 52.4742371, 9.9413628);
		// createSite("Celle", "CELL", 52.6455589, 9.9578078);
		// Site pein = createSite("Peine", "PEIN", 52.3161997, 10.1630724);
		// createSite("Wunstorf", "WUNF", 52.4313259, 9.31948230);
		// log.info("Dist EMDE - PEIN: " + EMDE.getDistanceTo(pein));
		// createSite("Borkum Riffgrund 1 (EDYB)",53.9736 ,6.5592);
		// createSite("Borkum Riffgrund 2",53.9617 ,6.4744);
		bwbe = getSiteByItemDesignation("BWBE");
		// createSite("BorWin B",54.3586 ,6.0169);
		// createSite("Butendiek (EKBU)",55.0214 ,7.7964);
		// createSite("DanTysk (EKDT)",55.1267 ,7.2372);
		dwal = getSiteByItemDesignation("DWAL");
		dwbe = getSiteByItemDesignation("DWBE");
		// createSite("Fino 1 (EDYF)",54.0239 ,6.5931);
		// createSite("Fino 3",55.2028 ,7.1525);
		// createSite("Gemini (Buitengaats)",54.0392 ,6.0339);
		// System.out.println(BWBE.getDistanceTo(dwal));

	}

	private Site getSiteByItemDesignation(String itemDesignation) {
		return dataManager.load(Site.class)
				.query("select s from lineup$Site s where s.itemDesignation = :itemDesignation")
				.parameter("itemDesignation", itemDesignation).one();
	}

	private CraftType getCraftTypeByType(String type) {
		return dataManager.load(CraftType.class).query("select s from lineup$CraftType s where s.name = :type")
				.parameter("type", type).one();
	}

	@Test
	public void testLoadUser() {
		try (Transaction tx = persistence.createTransaction()) {
			EntityManager em = persistence.getEntityManager();
			TypedQuery<User> query = em.createQuery("select u from sec$User u where u.login = :userLogin", User.class);
			query.setParameter("userLogin", "admin");
			List<User> users = query.getResultList();
			tx.commit();
			assertEquals(1, users.size());
		}
	}

	private Site createSite(String name, String itemDesignation, double lat, double lon) {
		Site site1 = metadata.create(Site.class);
		site1.setSiteName(name);
		site1.setItemDesignation(itemDesignation);
		site1.setLatitude(lat);
		site1.setLongitude(lon);
		dataManager.commit(site1);
		return site1;
	}

	@After
	public void tearDown() throws Exception {
		if (ccId != null) {
			dataManager.remove(dataManager.getReference(CrewChange.class, ccId));
			try (Transaction tx = persistence.createTransaction()) {
				List<Site> siteList = persistence.getEntityManager()
						.createQuery("select s from lineup$Site s where s.createdBy = 'test_admin'", Site.class)
						.getResultList();

				for (Site site : siteList) {
					persistence.getEntityManager().remove(site);
				}
				tx.commit();
			}
		}
	}

	@Test
	public void testCreateCrewChange() {

		// Hin 2 frei, Rück 3 frei von 12 -> 8+7 --> 19 Tickets
		ccId = createCC(2, 3);
		validateCrewChange(ccId);
	}

	@Test
	public void testAddWaypoint() {
		ccId = createCC(0, 0);
		Transfer transfer;

		// EMDE - BWBE + DWAL
		transfer = dataManager.load(Transfer.class).view("transfer-full")
				.query("select t from lineup$Transfer t where t.crewChange.id = :ccId").parameter("ccId", ccId).one();
		Waypoint wpDwal = metadata.create(Waypoint.class);
		wpDwal.setSite(dwal);
		wpDwal.setTransfer(transfer);
		dataManager.commit(wpDwal);
		transfer.getWaypoints().add(2, wpDwal);
		dataManager.commit(transfer);

		try (Transaction tx = persistence.createTransaction()) {
			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());
		}
	}

	@Test
	public void testReachableSites() {
		ccId = createCC(0, 0);
		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);

		}
		List<Site> reachableSites = travelOptionService.getReachableSites(transfer.getId());
		for (Site site2 : reachableSites) {
			log.debug(site2.getItemDesignation());
		}
		assertEquals(1, reachableSites.size());
	}

	@Test
	public void testRemoveWaypoint() {
		ccId = createCC(0, 0);
		Transfer transfer;
		Waypoint awp1;

		Waypoint wpDwal = metadata.create(Waypoint.class);

		// EMDE - BWBE + DWAL
		transfer = dataManager.load(Transfer.class).view("transfer-full")
				.query("select t from lineup$Transfer t where t.crewChange.id = :ccId").parameter("ccId", ccId).one();
		wpDwal.setSite(dwal);
		wpDwal.setTransfer(transfer);
		dataManager.commit(wpDwal);
		transfer.getWaypoints().add(2, wpDwal);
		dataManager.commit(transfer);

		assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());

		try (Transaction tx = persistence.createTransaction()) {

			cc = entityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());
			tx.commit();
		}

		Waypoint remove = transfer.getWaypoints().remove(1);
		dataManager.remove(remove);
		assertEquals("EMDE - DWAL - EMDE", transfer.getRouteShort());
		// dataManager.commit(transfer);

		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - DWAL - EMDE", transfer.getRouteShort());
		}

	}

	@Test
	public void testMyTrips() {

		// CrewChange erstellen
		ccId = createCC(12, 12);
		CrewChange cc;
		Transfer transfer;

		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
		}
		Waypoint wpDwal = metadata.create(Waypoint.class);
		// DWAL dem Transfer hinzufügen
		// EMDE - BWBE + DWAL
		transfer = dataManager.load(Transfer.class).view("transfer-full")
				.query("select t from lineup$Transfer t where t.crewChange.id = :ccId").parameter("ccId", ccId).one();
		wpDwal.setSite(dwal);
		wpDwal.setTransfer(transfer);
		dataManager.commit(wpDwal);
		transfer.getWaypoints().add(2, wpDwal);
		dataManager.commit(transfer);

		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			/** "EMDE (3) - BWBE (2) - DWAL - EMDE" */
			assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());

			Ticket ticket1 = metadata.create(Ticket.class);
			ticket1.setTransfer(transfer);
			ticket1.setStartSite(emde);
			ticket1.setDestinationSite(bwbe);

			Ticket ticket2 = metadata.create(Ticket.class);
			ticket2.setTransfer(transfer);
			ticket2.setStartSite(emde);
			ticket2.setDestinationSite(bwbe);

			Ticket ticket3 = metadata.create(Ticket.class);
			ticket3.setTransfer(transfer);
			ticket3.setStartSite(emde);
			ticket3.setDestinationSite(dwal);

			Ticket ticket4 = metadata.create(Ticket.class);
			ticket4.setTransfer(transfer);
			ticket4.setStartSite(bwbe);
			ticket4.setDestinationSite(dwal);

			persistence.getEntityManager().persist(ticket1);
			persistence.getEntityManager().persist(ticket2);
			persistence.getEntityManager().persist(ticket3);
			persistence.getEntityManager().persist(ticket4);

			// Ich will mit von BWBE zu DWAL
			/** "EMDE (3) - BWBE (2) - DWAL - EMDE" */
			FavoriteTrip trip = metadata.create(FavoriteTrip.class);
			trip.setStartSite(bwbe);
			trip.setDestination(dwal);
			entityManager().persist(trip);

			trip = metadata.create(FavoriteTrip.class);
			trip.setStartSite(emde);
			trip.setDestination(dwal);
			entityManager().persist(trip);

			trip = metadata.create(FavoriteTrip.class);
			trip.setStartSite(dwbe);
			trip.setDestination(dwal);
			entityManager().persist(trip);

			tx.commit();
		}

		List<TripDTO> groupedTickets = travelOptionService.getGroupedTickets(transfer);

		assertEquals(3, groupedTickets.size());
		assertEquals("BWBE", groupedTickets.get(0).getSiteA().getItemDesignation());
		assertEquals("DWAL", groupedTickets.get(0).getSiteB().getItemDesignation());
		assertEquals(1, groupedTickets.get(0).getBookedSeats().intValue());

		assertEquals("EMDE", groupedTickets.get(1).getSiteA().getItemDesignation());
		assertEquals("BWBE", groupedTickets.get(1).getSiteB().getItemDesignation());
		assertEquals(2, groupedTickets.get(1).getBookedSeats().intValue());

		assertEquals("EMDE", groupedTickets.get(2).getSiteA().getItemDesignation());
		assertEquals("DWAL", groupedTickets.get(2).getSiteB().getItemDesignation());
		assertEquals(1, groupedTickets.get(2).getBookedSeats().intValue());

		HashMap<UUID, Integer> bookedSeatsMap = travelOptionService.getBookedSeatsMap(groupedTickets, transfer);
		assertEquals(3, bookedSeatsMap.get(emde.getUuid()).intValue());
		assertEquals(2, bookedSeatsMap.get(bwbe.getUuid()).intValue());
		assertNull(bookedSeatsMap.get(dwal.getUuid()));

		// List<TripDTO> myTrips = service.getMyTrips(new Date(), new Date());
		// es werden die booked seats ausgegen nicht die verfügbaren
		// assertTrue(myTrips.get(0).getTransfer().getId().equals(transfer.getId()));

		// for (TripDTO tripDTO : myTrips) {
		// log.debug(tripDTO.toString());
		// }

	}

	@Test
	public void testMoveWaypoint() {

		ccId = createCC(0, 0);
		int distance = 0;
		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {

			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - EMDE", transfer.getRouteShort());
		}

		// erstmal einen WP hinzufügen
		// EMDE - BWBE + DWAL
		transfer = dataManager.load(Transfer.class).view("transfer-full")
				.query("select t from lineup$Transfer t where t.crewChange.id = :ccId").parameter("ccId", ccId).one();
		Waypoint wpDwal = metadata.create(Waypoint.class);
		wpDwal.setSite(dwal);
		wpDwal.setTransfer(transfer);
		dataManager.commit(wpDwal);
		transfer.getWaypoints().add(2, wpDwal);
		dataManager.commit(transfer);

		// neu laden
		Waypoint awp1;
		Waypoint wpBWBE;

		try (Transaction tx = persistence.createTransaction()) {
			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());

			awp1 = transfer.getWaypoints().get(0);
			wpBWBE = transfer.getWaypoints().get(1);
			assertEquals(wpBWBE.getSite().getItemDesignation(), "BWBE");
			wpDwal = transfer.getWaypoints().get(2);
			assertEquals(wpDwal.getSite().getItemDesignation(), "DWAL");
			distance = transfer.getTotalDistance();
			assertTrue(distance > 0);
		}
		int oldIndex = transfer.getWaypoints().indexOf(wpBWBE);
		int newIndex = oldIndex + 1;
		Collections.swap(transfer.getWaypoints(), oldIndex, newIndex);
		dataManager.commit(transfer);

		try (Transaction tx = persistence.createTransaction()) {
			// neu laden
			transfer = persistence.getEntityManager().find(Transfer.class, transfer.getId());
			assertEquals("EMDE - DWAL - BWBE - EMDE", transfer.getRouteShort());
		}
	}

	/**
	 * Beim Erstellen eines Transfers werden die Empfänger in den Favoriten in
	 * Abhängigkeit von der Notif-Einstellung benachrichtigt. Der Listener auf
	 * der Transfer-Entität reagiert und erstellt "Notification" - (sendSummary)
	 * Favoriten werden erstellt, ein CC wird erstellt, eine Benachrichtigung
	 * wird in der DB erwartet
	 */
	@Test
	public void testNotifyRecipient() {

		try (Transaction tx = persistence.createTransaction()) {
			// passenden Favorit mit Benachrichtigungswunsch erstellen
			// nicht passenden Favorit erstellen
			// Ich will mit von BWBE zu DWAL, DWBE kann nicht erreicht werden
			/** "EMDE - BWBE - DWAL - EMDE" */
			FavoriteTrip trip1 = metadata.create(FavoriteTrip.class);
			// erzeugt eine travelOption
			trip1.setStartSite(emde);
			trip1.setDestination(dwal);
			trip1.setEmailNotification(true);
			entityManager().persist(trip1);

			// erzeugt eine travelOption
			FavoriteTrip trip2 = metadata.create(FavoriteTrip.class);
			trip2.setStartSite(emde);
			trip2.setDestination(dwal);
			trip2.setEmailNotification(false);
			entityManager().persist(trip2);

			// erzeugt keine travelOption, weil dwbe außerhalb der Reichweite
			FavoriteTrip trip3 = metadata.create(FavoriteTrip.class);
			trip3.setStartSite(emde);
			trip3.setDestination(dwbe);
			trip3.setEmailNotification(true);
			entityManager().persist(trip3);
			tx.commit();
		}

		// CC erstellen EMDE - BWBE
		ccId = createCC(6, 6);

		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - EMDE", transfer.getRouteShort());
		}

		travelOptionService.sendTravelOptionNotification();

		List<TravelOption> list = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.transfer.id = :transferId")
				.parameter("transferId", transfer.getId()).view("mailNotification-manage").list();

		assertEquals(2, list.size());
		TravelOption notif = list.get(0);
		assertEquals(TravelOptionStatus.Init, notif.getStatus());
		assertNotNull(notif.getTransfer());
		assertNotNull(notif.getFavoriteTrip());

	}

	@Test
	public void testBookSeats() {

		// FT erstellen EMDE - DWAL
		createFavoriteTrip();
		// CC erstellen EMDE - BWBE
		ccId = createCC(6, 6);

		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - EMDE", transfer.getRouteShort());
			assertEquals(12, transfer.getTickets().size());
		}

		TravelOption travelOption = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.transfer.crewChange.id = :ccId")
				.parameter("ccId", ccId).one();

		travelOptionService.bookSeats(travelOption.getId(), 2);
		try (Transaction tx = persistence.createTransaction()) {
			travelOption = entityManager().find(TravelOption.class, travelOption.getId());
			assertEquals(2, travelOption.getBookedSeats().intValue());
			assertEquals(TravelOptionStatus.Requested, travelOption.getStatus());
		}

		travelOptionService.approveBooking(travelOption.getId());
		try (Transaction tx = persistence.createTransaction()) {
			TravelOption to = entityManager().find(TravelOption.class, travelOption.getId());
			assertEquals(2, to.getBookedSeats().intValue());
			assertEquals(TravelOptionStatus.Approved, to.getStatus());
			transfer = persistence.getEntityManager().find(Transfer.class, transfer.getId());
			assertEquals(14, transfer.getTickets().size());
		}

	}

	private FavoriteTrip createFavoriteTrip() {
		FavoriteTrip trip1 = metadata.create(FavoriteTrip.class);
		try (Transaction tx = persistence.createTransaction()) {
			// erzeugt eine travelOption
			trip1.setStartSite(emde);
			trip1.setDestination(dwal);
			trip1.setEmailNotification(true);
			entityManager().persist(trip1);
			tx.commit();
		}
		return trip1;
	}

	private UUID createCC(int freeSeatsWay1, int freeSeatsWay2) {
		CrewChangeCreateDTO dto = metadata.create(CrewChangeCreateDTO.class);
		dto.setStartDateTime(new Date());
		dto.setDepartureSite(emde);
		dto.setDestinationSite(bwbe);
		dto.setCraftType(getCraftTypeByType("AW139"));
		dto.setFreeSeatsOutbound(freeSeatsWay1);
		dto.setFreeSeatsInbound(freeSeatsWay2);

		return service.createCrewChange(dto);

	}

	private CraftType createCraftTypeX() {
		CraftType ct = null;
		try (Transaction tx = persistence.createTransaction()) {
			ct = metadata.create(CraftType.class);
			ct.setName("AW123");
			ct.setMaxRange(270);
			ct.setSeats(10);
			persistence.getEntityManager().persist(ct);
			tx.commit();
		}
		return ct;
	}

	private void validateCrewChange(UUID ccId) {
		try (Transaction tx = persistence.createTransaction()) {

			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			assertTrue(cc.getTransfers().size() == 1);

			validateTransfer(cc.getTransfers().get(0));
		}
	}

	private void validateTransfer(Transfer transfer) {
		assertNotNull(transfer);
		assertNotNull(transfer.getCraftType());
		Waypoint awp1 = transfer.getWaypoints().get(0);
		assertEquals(awp1.getSite().getItemDesignation(), "EMDE");

		Waypoint wp1 = transfer.getWaypoints().get(1);
		assertNotNull(wp1);
		assertEquals(wp1.getSite().getItemDesignation(), "BWBE");

		assertTrue(transfer.getTotalDistance() > 100);
		assertEquals("EMDE - BWBE - EMDE", transfer.getRouteShort());
		Query query = persistence.getEntityManager()
				.createQuery("select t from lineup$Ticket t where t.transfer.id = :transferId", Ticket.class)
				.setParameter("transferId", transfer.getId());
		List<Ticket> ticketList = query.getResultList();
		assertEquals(19, ticketList.size());
	}

}
