package eu.crewbase.lineup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.haulmont.cuba.testsupport.TestContainer;
import eu.crewbase.lineup.entity.coredata.CraftType;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.coredata.SiteCategory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.ClassRule;

import com.haulmont.bali.util.Dom4j;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.service.EntityService;

public class LineupTestContainer extends com.haulmont.cuba.testsupport.TestContainer {

    @ClassRule
	public static LineupTestContainer cont = new LineupTestContainer();
    
	protected Metadata metadata;
	protected DataManager dataManager;
	protected Persistence persistence;
	protected EntityService entityService;
	
	public LineupTestContainer() {
				
        appComponents = new ArrayList<>(Arrays.asList(
                "com.haulmont.cuba"
                // add CUBA premium add-ons here
                // "com.haulmont.bpm",
                // "com.haulmont.charts",
                // "com.haulmont.fts",
                // "com.haulmont.reports",
                // and custom app components if any
        ));
        appPropertiesFiles = Arrays.asList(
                // List the files defined in your web.xml
                // in appPropertiesConfig context parameter of the core module
                "eu/crewbase/lineup/app.properties"
                // Add this file which is located in CUBA and defines some properties
                // specifically for test environment. You can replace it with your own
                // or add another one in the end.
                ,"test-app.properties"
                );
        initDbProperties();
    }

    protected void setUp() throws Exception{
        metadata = cont.metadata();
        persistence = cont.persistence();
        dataManager = AppBeans.get(DataManager.class);
        entityService = AppBeans.get(EntityService.NAME);
    }
    
    private void initDbProperties() {
        File contextXmlFile = new File("modules/core/web/META-INF/context.xml");
        if (!contextXmlFile.exists()) {
            contextXmlFile = new File("web/META-INF/context-test.xml");
        }
        if (!contextXmlFile.exists()) {
            throw new RuntimeException("Cannot find 'context.xml' file to read database connection properties. " +
                    "You can set them explicitly in this method.");
        }
        Document contextXmlDoc = Dom4j.readDocument(contextXmlFile);
        Element resourceElem = contextXmlDoc.getRootElement().element("Resource");

        dbDriver = resourceElem.attributeValue("driverClassName");
        dbUrl = resourceElem.attributeValue("url");
        dbUser = resourceElem.attributeValue("username");
        dbPassword = resourceElem.attributeValue("password");
    }

    protected CraftType getCraftTypeByType(String type) {
        return dataManager.load(CraftType.class).query("select s from lineup$CraftType s where s.name = :type")
                .parameter("type", type).one();
    }

    protected Site createSite(String name, String itemDesignation, double lat, double lon, SiteCategory category) {
        Site site1 = metadata.create(Site.class);
        site1.setSiteName(name);
        site1.setItemDesignation(itemDesignation);
        site1.setLatitude(lat);
        site1.setLongitude(lon);
        site1.setSiteCategory(category);
        dataManager.commit(site1);
        return site1;
    }

    public static class Common extends LineupTestContainer {

        public static final LineupTestContainer.Common INSTANCE = new LineupTestContainer.Common();

        private static volatile boolean initialized;

        private Common() {
        }

        @Override
        public void before() throws Throwable {
            if (!initialized) {
                super.before();
                initialized = true;
            }
            setupContext();
        }

        @Override
        public void after() {
            cleanupContext();
            // never stops - do not call super
        }
    }
}