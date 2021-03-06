package eu.crewbase.lineup.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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
import eu.crewbase.lineup.entity.coredata.SiteCategory;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Ticket;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.TravelOption;
import eu.crewbase.lineup.entity.wayfare.TravelOptionStatus;
import eu.crewbase.lineup.entity.wayfare.Waypoint;
import eu.crewbase.lineup.service.CrewChangeService;
import eu.crewbase.lineup.service.TransferService;
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
	private TransferService transferService;

	//erstmal aufräumen, alle CC werden entfernt, diese bleiben nach dem Test stehen, damit ich besser debuggen kann.
	//eigentlich eine doofe Begründung
	//deshalb eigentlich später den ganzen Mist auch in teardown aufrufen und ggf. auskommentieren

	@Before
	public void setUp() throws Exception {
		super.setUp();
		log.warn("GEHT LOS!");
		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);

		cleanUp();

		service = AppBeans.get(CrewChangeService.NAME);
		travelOptionService = AppBeans.get(TravelOptionService.NAME);
		transferService = AppBeans.get(TransferService.NAME);
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

	private Site createSiteX(String name, String itemDesignation, double lat, double lon, SiteCategory category) {
		Site site1 = super.createSite(name, itemDesignation, lat, lon, category);
		return site1;
	}

	@After
	public void tearDown() throws Exception {
		if (ccId != null) {
			//dataManager.remove(dataManager.getReference(CrewChange.class, ccId));
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
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange crewChange = persistence.getEntityManager().find(CrewChange.class, ccId);
			persistence.getEntityManager().remove(crewChange);
			tx.commit();
		}
	}

	@Test
	public void testAddWaypoint() {
		ccId = createCC(0, 0);
		Transfer transfer;
		persistence.getTransaction().commit();
		// EMDE - BWBE + DWAL
		transfer = dataManager.load(Transfer.class).view("transfer-full")
				.query("select t from lineup$Transfer t where t.crewChange.id = :ccId").parameter("ccId", ccId).one();

		transferService.addWaypoint(transfer.getId(), dwal.getId(), 2);

		try (Transaction tx = persistence.createTransaction()) {
			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());
		}
	}
	@Test
	public void testAddTwoWaypoint() {
		ccId = createCC(0, 0);
		Transfer transfer;

		try (Transaction tx = persistence.createTransaction()) {
			cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			Waypoint wp1 = metadata.create(Waypoint.class);
			Waypoint wp2 = metadata.create(Waypoint.class);
			wp1.setSite(bwbe);
			wp2.setSite(dwal);

			transfer.addWaypointShortestWay(wp1,wp2);
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

		transferService.addWaypoint(transfer.getId(), dwal.getId(), 2);

		try (Transaction tx = persistence.createTransaction()) {

			cc = entityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());
			tx.commit();
		}

		transferService.removeWaypoint(transfer.getId(), 1);

		try (Transaction tx = persistence.createTransaction()) {

			cc = entityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - DWAL - EMDE", transfer.getRouteShort());
		}

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

		transferService.addWaypoint(transfer.getId(), dwal.getId());
		
		try (Transaction tx = persistence.createTransaction()) {

			cc = entityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);

			assertEquals("EMDE - DWAL - BWBE - EMDE", transfer.getRouteShort());
			tx.commit();
		}
		
		transferService.moveWaypoint(transfer.getId(), bwbe.getId(), 1);
		
		try (Transaction tx = persistence.createTransaction()) {

			cc = entityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - DWAL - EMDE", transfer.getRouteShort());
			tx.commit();
		}
		

		//Collections.swap(transfer.getWaypoints(), oldIndex, newIndex);
		
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
			// - passenden Favorit mit Benachrichtigungswunsch erstellen
			// - nicht passenden Favorit erstellen
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
		ccId = createCC(1, 2);

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
		ccId = createCC(5, 8); //Kapa  2*12, 13 Frei, 7+4 Tickets müssen zunächst erzeugt werden

		//Transfer checken
		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - EMDE", transfer.getRouteShort());
			assertEquals(11, transfer.getTickets().size());
		}

		//TravelOption muss erzeugt worden sein
		TravelOption travelOption = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.transfer.crewChange.id = :ccId")
				.parameter("ccId", ccId).one();

		//5 von 12 sind frei EMDE nach DWAL
		assertEquals(5, travelOption.getAvailableSeats().intValue());

		//2 der 13 freien Plätze buchen
		travelOptionService.bookSeats(travelOption.getId(), 2);
		try (Transaction tx = persistence.createTransaction()) {
			travelOption = entityManager().find(TravelOption.class, travelOption.getId());
			assertEquals(2, travelOption.getBookedSeats().intValue());
			assertEquals(TravelOptionStatus.Requested, travelOption.getStatus());
		}
		//die 2 Plätze bestätigen, jetzt sind es 13 Tickets
		travelOptionService.approveBooking(travelOption.getId());
		try (Transaction tx = persistence.createTransaction()) {
			TravelOption to = entityManager().find(TravelOption.class, travelOption.getId());
			assertEquals(2, to.getBookedSeats().intValue());
			assertEquals(2, to.getBookedTickets().size());
			assertEquals(TravelOptionStatus.Approved, to.getStatus());
			transfer = persistence.getEntityManager().find(Transfer.class, transfer.getId());

			//13 Tickets
			assertEquals(13, transfer.getTickets().size());
		}

	}

	@Test
	public void testApproveBooking() {
		removeTravelOptions();
		// FT erstellen EMDE - DWAL
		FavoriteTrip favTrip1 = createFavoriteTrip(emde, dwal);
		FavoriteTrip favTrip2 = createFavoriteTrip(emde, bwbe);
		FavoriteTrip favTrip3 = createFavoriteTrip(dwal, bwbe);

		// CC erstellen EMDE - BWBE
		ccId = createCC(3,5); //3+5 frei

		List<TravelOption> travelOptionList = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.transfer.crewChange.id = :ccId")
				.parameter("ccId", ccId).list();
		assertEquals(3, travelOptionList.size());

		TravelOption travelOption1 = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.favoriteTrip.id=:favTripId")
				.parameter("favTripId", favTrip1.getId()).one();
		TravelOption travelOption2 = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.favoriteTrip.id=:favTripId")
				.parameter("favTripId", favTrip2.getId()).one();
		TravelOption travelOption3 = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.favoriteTrip.id=:favTripId")
				.parameter("favTripId", favTrip3.getId()).one();
		assertEquals(3, travelOption1.getAvailableSeats().intValue());
		assertEquals(3, travelOption2.getAvailableSeats().intValue());
		assertEquals(5, travelOption3.getAvailableSeats().intValue());

		travelOptionService.bookSeats(travelOption1.getId(), 2);
		travelOption1 = dataManager.load(TravelOption.class)
				.query("select m from lineup$TravelOption m where m.favoriteTrip.id=:favTripId")
				.parameter("favTripId", favTrip1.getId()).one();
		assertEquals(1, travelOption1.getAvailableSeats().intValue());
		assertEquals(2, travelOption1.getBookedSeats().intValue());

		travelOptionService.approveBooking(travelOption1.getId());

		try (Transaction tx = persistence.createTransaction()) {
			travelOption1 = persistence.getEntityManager()
					.createQuery("select m from lineup$TravelOption m where m.favoriteTrip.id=:favTripId", TravelOption.class)
					.setParameter("favTripId", favTrip1.getId()).getFirstResult();
			travelOption2 = persistence.getEntityManager()
					.createQuery("select m from lineup$TravelOption m where m.favoriteTrip.id=:favTripId", TravelOption.class)
					.setParameter("favTripId", favTrip2.getId()).getFirstResult();
			assertEquals(1, travelOption1.getAvailableSeats().intValue());
			assertEquals(1, travelOption2.getAvailableSeats().intValue());
			assertEquals(5, travelOption3.getAvailableSeats().intValue());

			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			assertEquals("EMDE - DWAL - BWBE - EMDE", travelOption1.getTransfer().getRouteShort());

		}

	}

	@Test
	public void testComplexTransfer() {
		// Transfer EMDN - BWBE - DWAL - DWBE - EMDE
		removeTravelOptions();

		// FT erstellen EMDE - DWAL
		FavoriteTrip favTrip1 = createFavoriteTrip(emde, bwbe); // 10
		FavoriteTrip favTrip2 = createFavoriteTrip(emde, dwbe); // 4, sollte
																// nicht
																// funktionieren
		FavoriteTrip favTrip3 = createFavoriteTrip(bwbe, emde); //

		// +14 -10 +4 -4
		// New Route: EMDE - DWAL - BWBE - DWBE - EMDE

		// CC erstellen EMDE - BWBE
		ccId = createCC(12, 12);
		Transfer transfer;
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - BWBE - EMDE", transfer.getRouteShort());
			assertEquals(0, transfer.getTickets().size());
		}
		
		transferService.removeWaypoint(transfer.getId(), bwbe.getId());
		
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			cc = entityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - EMDE", transfer.getRouteShort());
			assertEquals(0, transfer.getTickets().size());
		}
		
		assertTrue(transferService.addWaypoint(transfer.getId(), dwbe.getId()));
		
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - DWBE - EMDE", transfer.getRouteShort());
			assertEquals(0, transfer.getTickets().size());
		}
		
		assertTrue(transferService.addWaypoint(transfer.getId(), dwal.getId()));
		
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			assertEquals("EMDE - DWAL - DWBE - EMDE", transfer.getRouteShort());
			assertEquals(0, transfer.getTickets().size());
		}
		
		
		assertFalse(transferService.addWaypoint(transfer.getId(), bwbe.getId()));
		
		try (Transaction tx = persistence.createTransaction()) {
			CrewChange cc = persistence.getEntityManager().find(CrewChange.class, ccId);
			transfer = cc.getTransfers().get(0);
			//assertEquals("EMDE - DWAL - DWBE - BWBE - EMDE", transfer.getRouteShort());
			assertEquals(0, transfer.getTickets().size());
		}
		
		try (Transaction tx = persistence.createTransaction()) {

			favTrip1 = persistence.getEntityManager().find(FavoriteTrip.class, favTrip1.getId());
			favTrip2 = persistence.getEntityManager().find(FavoriteTrip.class, favTrip2.getId());
			favTrip3 = persistence.getEntityManager().find(FavoriteTrip.class, favTrip3.getId());

			travelOptionService.bookSeats(favTrip1.getTravelOption().get(0).getId(), 10);
			travelOptionService.bookSeats(favTrip2.getTravelOption().get(0).getId(), 4);

		}

	}

	private void removeTravelOptions() {
		List<TravelOption> delList = dataManager.load(TravelOption.class).list();
		for (TravelOption travelOption : delList) {
			dataManager.remove(travelOption);
		}
	}

	private FavoriteTrip createFavoriteTrip(Site siteA, Site siteB) {
		FavoriteTrip trip1 = metadata.create(FavoriteTrip.class);
		try (Transaction tx = persistence.createTransaction()) {
			// erzeugt eine travelOption
			trip1.setStartSite(siteA);
			trip1.setDestination(siteB);
			trip1.setEmailNotification(true);
			entityManager().persist(trip1);
			tx.commit();
		}
		return trip1;

	}

	private FavoriteTrip createFavoriteTrip() {
		return createFavoriteTrip(emde, dwal);
	}

	private UUID createCC(int freeSeatsWay1, int freeSeatsWay2) {
		return createCC(freeSeatsWay1, freeSeatsWay2,emde, bwbe );
	}
	private UUID createCC(int freeSeatsWay1, int freeSeatsWay2, Site site1, Site site2) {
		CrewChangeCreateDTO dto = metadata.create(CrewChangeCreateDTO.class);
		dto.setStartDateTime(new Date());
		dto.setDepartureSite(site1);
		dto.setDestinationSite(site2);
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

	private void cleanUp(){
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

			List<FavoriteTrip> favList = persistence.getEntityManager()
					.createQuery("select s from lineup$FavoriteTrip s where s.createdBy = 'test_admin'",
							FavoriteTrip.class)
					.getResultList();
			for (FavoriteTrip fav : favList) {
				persistence.getEntityManager().remove(fav);
			}
			tx.commit();
		}
	}

}
