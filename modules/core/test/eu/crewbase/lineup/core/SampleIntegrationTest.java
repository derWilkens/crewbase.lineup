package eu.crewbase.lineup.core;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.service.DeepLinkService;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleIntegrationTest {

    @ClassRule
    public static LineupTestContainer cont = LineupTestContainer.Common.INSTANCE;

    private Metadata metadata;
    private Persistence persistence;
    private DataManager dataManager;
    private DeepLinkService deepLinkService;

    @Before
    public void setUp() throws Exception {
        metadata = cont.metadata();
        persistence = cont.persistence();
        dataManager = AppBeans.get(DataManager.class);
        deepLinkService = AppBeans.get(DeepLinkService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoadUser() {
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            TypedQuery<User> query = em.createQuery(
                    "select u from sec$User u where u.login = :userLogin", User.class);
            query.setParameter("userLogin", "admin");
            List<User> users = query.getResultList();
            tx.commit();
            assertEquals(1, users.size());
            System.out.println(deepLinkService.generateDeepLinkForEntity(users.get(0)));
        }
    }
}