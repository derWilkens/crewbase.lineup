package eu.crewbase.lineup.core;

import org.junit.Before;
import org.junit.Test;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import eu.crewbase.lineup.LineupTestContainer;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.coredata.SiteCategory;

public class InsertNewItems extends LineupTestContainer {
	@Before
	public void setUp() throws Exception {
		super.setUp();
		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);

		SiteCategory category = dataManager.load(SiteCategory.class)
				.query("select e from lineup$SiteCategory e where e.categoryName = :type")
				.parameter("type", "Helideck")
				.one();
		createSite("alpha ventus", "EDYV", 54, 6.62777777777778, category);
		createSite("Amrumbank West", "EDYA", 54.5219444444444, 7.73583333333333, category);
		createSite("Bard1", "", 54.3527777777778, 6.01666666666667, category);
		createSite("Borkum Riffgrund 1", "EDYB", 53.9736111111111, 6.57611111111111, category);
		createSite("Borkum Riffgrund 2", "", 53.9616666666667, 6.47916666666667, category);
		createSite("BorWin alpha", "EHHJ", 54.3569444444444, 6.03055555555556, category);
		//createSite("BorWin beta", "", 54.3586111111111, 6.03, category);
		createSite("Butendiek", "EKBU", 55.0213888888889, 7.81055555555556, category);
		createSite("DanTysk", "EKDT", 55.1266666666667, 7.23388888888889, category);
		//createSite("DolWin alpha", "", 54.0061111111111, 6.42388888888889, category);
		//createSite("DolWin beta", "EDYD", 53.9858333333333, 6.92805555555556, category);
		createSite("Fino 1", "", 54.025, 6.59055555555556, category);
		createSite("Fino 3", "", 55.2025, 7.16361111111111, category);
		createSite("Gemini", "", 54.0391666666667, 6.0475, category);
		createSite("Global Tech1", "EHHI", 54.5225, 6.36972222222222, category);
		createSite("Gode Wind 01", "EDYG", 54.0302777777778, 7.01472222222222, category);
		createSite("Gode Wind 02", "EDYZ", 54.0594444444444, 7.04166666666667, category);
		createSite("HelWin alpha", "EDYH", 54.4536111111111, 7.74444444444444, category);
		createSite("Hohe See", "", 54.4519444444444, 6.335, category);
		createSite("Meerwind S/O", "EDYM", 54.41, 7.71055555555556, category);
		createSite("Merkur", "", 54.04, 6.56833333333333, category);
		createSite("Nordsee One", "EDYN", 53.9927777777778, 6.83805555555556, category);
		createSite("Riffgat", "EHNR", 53.6958333333333, 6.48361111111111, category);
		createSite("Sandbank", "EKSF", 55.2094444444444, 6.85805555555556, category);
		createSite("SylWin alpha", "", 55.0705555555556, 7.24611111111111, category);
		createSite("Trianel Windpark Borkum", "", 54.0555555555556, 6.46666666666667, category);
		createSite("Veja Mate", "EHGL", 54.3258333333333, 5.89222222222222, category);
		createSite("Wikinger", "EDYW", 54.835, 14.0794444444444, category);


	}

	@Test
	public void testLoadUser() {

	}
}
