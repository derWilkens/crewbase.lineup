package eu.crewbase.lineup.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.chile.core.model.Instance;
import com.haulmont.cuba.core.global.GlobalConfig;

@Service(DeepLinkService.NAME)
public class DeepLinkServiceBean implements DeepLinkService {
	@Inject
	private GlobalConfig globalConfig;

	@Override
	public String generateDeepLinkForEntity(Instance entityInstance) {

		String webAppUrl = globalConfig.getWebAppUrl();

		String className = entityInstance.getMetaClass().getName();
		String screen = className + ".edit";
		String item = className + "-" + entityInstance.getValue("id");
		String deepLink = webAppUrl + "/open?screen=" + screen + "item=" + item;

		return deepLink;
	}
}