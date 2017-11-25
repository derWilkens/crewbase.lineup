package eu.crewbase.lineup.listener;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

import eu.crewbase.lineup.entity.coredata.OffshoreUser;

@Component("LINEUP_OffshoreUserEntityListener")
public class OffshoreUserEntityListener implements BeforeInsertEntityListener<OffshoreUser> {


    @Override
    public void onBeforeInsert(OffshoreUser entity, EntityManager entityManager) {
//    	if (entity.getPassword() == null){
//    		entity.setPassword(getRandomPassword());
//    		entity.setChangePasswordAtNextLogon(true);
//    	}
//    	if (entity.getLogin() == null){
//    		entity.setLogin(entity.getEmail());
//    		entity.setLoginLowerCase(entity.getLogin().toLowerCase());
//    	}
    	
    }

	private String getRandomPassword() {
	    StringBuffer password = new StringBuffer(20);
	    int next = RandomUtils.nextInt(13) + 8;
	    password.append(RandomStringUtils.randomAlphanumeric(next));
	    return password.toString();
	}


}