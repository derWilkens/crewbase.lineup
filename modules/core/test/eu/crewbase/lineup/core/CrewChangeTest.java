package eu.crewbase.lineup.core;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


public class CrewChangeTest {


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
	@ClassRule
	public static LineupTestContainer cont = new LineupTestContainer();
	
	@Before
	public void setUp() throws Exception {
		
		user = cont.persistence().createTransaction().execute(em -> {
			User user = new User();
			user.setName("testCustomer");
			em.persist(user);
			return user;
		});
		operatedBy = cont.persistence().createTransaction().execute(em ->{
			Company comp = new Company();
			//comp.setClient(1);
			comp.setCompanyName("CompName");
			comp.setContactPerson("SinglePointOfContactInComp");
			comp.setEmail("operatorComp@derWilkens.de");
			return comp;
		});
			
		modeOfTransfer = cont.persistence().createTransaction().execute(em ->{
			ModeOfTransfer modeOfTransfer = new ModeOfTransfer();
			modeOfTransfer.setMode("Helicopter");
			return modeOfTransfer;
		});
		
		
		crewChange = cont.persistence().createTransaction().execute(em ->{
			CrewChange crewChange = new CrewChange();
			crewChange.setClient(1);
			crewChange.setFlightDate(new Date());
			return crewChange;
		});
		
		Transfer transfer1 = cont.persistence().createTransaction().execute(em ->{
			Transfer transfer = new Transfer();
			transfer.setClient(1);
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
			wp.setClient(1);
			wp.setSite(hus);
			return wp;
		}));
		waypointList.add(cont.persistence().createTransaction().execute(em ->{
			Waypoint wp = new Waypoint();
			wp.setClient(1);
			wp.setSite(sw1);
			return wp;
		}));
		waypointList.add(cont.persistence().createTransaction().execute(em ->{
			Waypoint wp = new Waypoint();
			wp.setClient(1);
			wp.setSite(hus);
			return wp;
		}));
	}
    @After
    public void tearDown() throws Exception {
        cont.deleteRecord(user);
    }
    
    
    @Test
    public void test() {
    	
    	List<Transfer> transfers = crewChange.getTransfers();
    	assertTrue(transfers.size() == 1);
    	Transfer transfer1 = transfers.iterator().next();
    	//transfer1.addWaypoint(hus,null);
    	transfer1.getWaypointList();
    	
    		
        try (Transaction tx = cont.persistence().createTransaction()) {
            EntityManager em = cont.persistence().getEntityManager();
            TypedQuery<User> query = em.createQuery(
                "select u from sec$User u", User.class);
            List<User> list = query.getResultList();
            tx.commit();
            assertTrue(list.size() > 0);
        }
    }
    
}
