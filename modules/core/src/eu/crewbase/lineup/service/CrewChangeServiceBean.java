package eu.crewbase.lineup.service;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.entity.CrewChange;
import eu.crewbase.lineup.entity.Transfer;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;

@Service(CrewChangeService.NAME)
public class CrewChangeServiceBean implements CrewChangeService {
	@Inject
	private Persistence persistence;

	@Inject
	private Metadata metadata;
	
	@Override
	public int createCrewChange(CrewChangeCreateDTO dto) {
		// TODO Auto-generated method stub
		try (Transaction tx = persistence.createTransaction()) {

			CrewChange cc = metadata.create(CrewChange.class);
			cc.setFlightDate(dto.getCrewChangeDate());
			Transfer transfer = metadata.create(Transfer.class);
			transfer.setTransferOrderNo(1);
			transfer.setCrewChange(cc);
			// transfer.setModeOfTransfer();
			// transfer.setOperatedBy(dto);

			transfer.addWaypoint(dto.getStartSite(), 1);
			transfer.addWaypoint(dto.getDestinationSite(), 2);
			transfer.addWaypoint(dto.getStartSite(), 3);
			java.util.List<Transfer> transferList = new ArrayList<Transfer>();
			transferList.add(transfer);
			cc.setTransfers(transferList);
			
			persistence.getEntityManager().persist(cc);
			tx.commit();
		}
		return 1;

	}

}