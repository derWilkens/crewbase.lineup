package eu.crewbase.lineup.service;

import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.wayfare.Ticket;
import eu.crewbase.lineup.entity.wayfare.Transfer;

@Service(TransferService.NAME)
public class TransferServiceBean implements TransferService {

	private static final Logger log = LoggerFactory.getLogger(TransferServiceBean.class);

	@Inject
	public Persistence persistence;
	@Inject
	public DataManager dataManager;
	@Inject
	public Metadata metadata;
	@Inject
	public TravelOptionService travelOptionService;

	@Override
	public void createTickets(UUID transferId, Site siteA, Site siteB, int bookedSeats) {
		try (Transaction tx = persistence.createTransaction()) {
			Transfer transfer = persistence.getEntityManager().find(Transfer.class, transferId);

			for (int i = 0; i < bookedSeats; i++) {
				Ticket ticket = metadata.create(Ticket.class);
				ticket.setTransfer(transfer);
				ticket.setStartSite(siteA);
				ticket.setDestinationSite(siteB);
				transfer.getTickets().add(ticket);
			}
			persistence.getEntityManager().persist(transfer);
			tx.commit();
		}
	}

}