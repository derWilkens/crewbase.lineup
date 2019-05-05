package eu.crewbase.lineup.core;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import eu.crewbase.lineup.entity.coredata.SiteCategory;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.coredata.Company;
import eu.crewbase.lineup.entity.coredata.ModeOfTransfer;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.entity.wayfare.Waypoint;


public class CrewChangeTest  extends LineupTestContainer{


	private User user;
	private CrewChange crewChange;
	private Company operatedBy;
	private ModeOfTransfer modeOfTransfer;
	private ArrayList<Waypoint> waypointList;
	private Site hus;
	private Site sw1;

	/**
	 * Kompletten CrewChange anlegen
	 */

	@Before
	public void setUp() throws Exception {

		SiteCategory category = super.dataManager.load(SiteCategory.class)
				.query("select e from lineup$SiteCategory e where e.categoryName = :type")
				.parameter("type", "Helideck")
				.one();

		hus = createSite("Husum", "", 53.9616666666667, 6.47916666666667, category);
		sw1 = createSite("SylWin alpha", "SWAL", 54.3569444444444, 6.03055555555556, category);


		operatedBy = cont.persistence().createTransaction().execute(em ->{
			Company comp = new Company();
			//comp.setClient(1);
			comp.setCompanyName("CompName");
			comp.setContactPerson("SinglePointOfContactInComp");
			comp.setEmail("operatorComp@derWilkens.de");
			em.persist(comp);
			return comp;
		});
			
		modeOfTransfer = cont.persistence().createTransaction().execute(em ->{
			ModeOfTransfer modeOfTransfer = new ModeOfTransfer();
			modeOfTransfer.setMode("Helicopter");
			em.persist(modeOfTransfer);
			return modeOfTransfer;
		});
		
		
		crewChange = cont.persistence().createTransaction().execute(em ->{
			CrewChange crewChange = new CrewChange();
			crewChange.setClient(1);
			crewChange.setStartDate(new Date());
			return crewChange;
		});
		
		Transfer transfer1 = cont.persistence().createTransaction().execute(em ->{
			Transfer transfer = new Transfer();
			//transfer.setClient(1);
			transfer.setModeOfTransfer(modeOfTransfer);
			transfer.setOperatedBy(operatedBy);
			transfer.setTransferOrderNo(1);
			//transfer.setWaypointList(waypointList);
			return transfer;
		});
		crewChange.setTransfers(new ArrayList<>());
		crewChange.getTransfers().add(transfer1);
		
		waypointList = new ArrayList<Waypoint>();
		waypointList.add(cont.persistence().createTransaction().execute(em ->{
			Waypoint wp = new Waypoint();
			//wp.setClient(1);
			wp.setSite(hus);
			return wp;
		}));
		waypointList.add(cont.persistence().createTransaction().execute(em ->{
			Waypoint wp = new Waypoint();
			//wp.setClient(1);
			wp.setSite(sw1);
			return wp;
		}));
		waypointList.add(cont.persistence().createTransaction().execute(em ->{
			Waypoint wp = new Waypoint();
			//wp.setClient(1);
			wp.setSite(hus);
			return wp;
		}));
	}
    @After
    public void tearDown() throws Exception {
		cont.deleteRecord(crewChange);
        cont.deleteRecord(user);
    }
    
    
    @Test
    public void test() {

    	List<Transfer> transfers = crewChange.getTransfers();
    	assertTrue(transfers.size() == 1);
    	Transfer transfer1 = transfers.iterator().next();
    	//transfer1.addWaypoint(hus,null);
    	//transfer1.getWaypointList();
    	
    		
        try (Transaction tx = cont.persistence().createTransaction()) {
            EntityManager em = cont.persistence().getEntityManager();
            TypedQuery<User> query = em.createQuery(
                "select u from sec$User u", User.class);
            List<User> list = query.getResultList();
            tx.commit();
            assertTrue(list.size() > 0);
        }
    }

	private UUID createCC(int freeSeatsWay1, int freeSeatsWay2) {
		CrewChangeCreateDTO dto = metadata.create(CrewChangeCreateDTO.class);
		dto.setStartDateTime(new Date());
		dto.setDepartureSite(sw1);
		dto.setDestinationSite(hus);
		dto.setCraftType(getCraftTypeByType("AW139"));
		dto.setFreeSeatsOutbound(freeSeatsWay1);
		dto.setFreeSeatsInbound(freeSeatsWay2);

		//return service.createCrewChange(dto);
	return null;
	}
}
