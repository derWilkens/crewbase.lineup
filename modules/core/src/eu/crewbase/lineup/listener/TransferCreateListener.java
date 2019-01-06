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

@Component("lineup_TransferCreateListener")
public class TransferCreateListener implements AfterInsertEntityListener<Transfer> {

	@Inject
	private CrewChangeService crewChangeService;

	@Override
	public void onAfterInsert(Transfer transfer, Connection connection) {
		//crewChangeService.createMailNotifycations(transfer);
	}

}