package eu.crewbase.lineup.listener;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.listener.AfterInsertEntityListener;
import com.haulmont.cuba.security.entity.User;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.FavoriteTrip;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.service.CrewChangeService;
import com.haulmont.cuba.core.listener.AfterUpdateEntityListener;

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