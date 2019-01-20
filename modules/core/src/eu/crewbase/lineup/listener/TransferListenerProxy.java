package eu.crewbase.lineup.listener;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.AfterInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

import eu.crewbase.lineup.entity.wayfare.Transfer;
import com.haulmont.cuba.core.listener.AfterUpdateEntityListener;

@Component("lineup_TransferListenerProxy")
public class TransferListenerProxy
		implements BeforeInsertEntityListener<Transfer>, AfterInsertEntityListener<Transfer>, AfterUpdateEntityListener<Transfer> {

	private static final Logger log = LoggerFactory.getLogger(TransferListenerProxy.class);

	@Override
	public void onBeforeInsert(Transfer entity, EntityManager entityManager) {
		log.debug("------------------BEFORE INSERT transfer");
	}

	@Override
	public void onAfterInsert(Transfer entity, Connection connection) {
		log.debug("------------------after INSERT transfer");
	}

    @Override
    public void onAfterUpdate(Transfer entity, Connection connection) {
    	log.debug("------------------after UPDATE transfer");
    }


}