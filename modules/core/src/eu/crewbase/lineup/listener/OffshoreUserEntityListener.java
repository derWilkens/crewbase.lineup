package eu.crewbase.lineup.listener;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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
	    //int next = (int) RandomUtils.nextInt(1,10);
	    //password.append(RandomStringUtils.randomAlphanumeric(8));
	    //return password.toString();
	    return RandomStringUtils.randomAlphanumeric(8);
	}


}