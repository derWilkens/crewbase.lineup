package eu.crewbase.lineup.listener;

import java.sql.Connection;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.listener.AfterInsertEntityListener;
import com.haulmont.cuba.core.listener.AfterUpdateEntityListener;

import eu.crewbase.lineup.entity.wayfare.Transfer;

@Component("lineup_TransferListener")
public class TransferListener implements AfterInsertEntityListener<Transfer>, AfterUpdateEntityListener<Transfer> {

	@Override
	public void onAfterInsert(Transfer transfer, Connection connection) {
		transfer.renumber();
	}

    @Override
    public void onAfterUpdate(Transfer entity, Connection connection) {
    	entity.renumber();
    }


}