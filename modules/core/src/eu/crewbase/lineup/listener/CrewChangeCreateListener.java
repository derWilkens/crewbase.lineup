package eu.crewbase.lineup.listener;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.listener.AfterInsertEntityListener;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.wayfare.CrewChange;
import eu.crewbase.lineup.entity.wayfare.Transfer;
import eu.crewbase.lineup.service.CrewChangeService;

@Component("lineup_CrewChangeCreateListener")
public class CrewChangeCreateListener implements AfterInsertEntityListener<CrewChange> {

	@Inject
	private CrewChangeService crewChangeService;

	@Override
	public void onAfterInsert(CrewChange entity, Connection connection) {
		// sind Plätze frei?

		List<Site> siteList = new ArrayList<Site>();
		// welche sites lassen sich erreichen?
		for (Transfer transfer : entity.getTransfers()) {
			if (transfer.getTickets().size() < transfer.getCraftType().getSeats()) {
				siteList.addAll(crewChangeService.getReachableSites(transfer.getId()));
			}
		}

		// wer möchte über freie Plätze zu den sites informiert werden?

	}

}