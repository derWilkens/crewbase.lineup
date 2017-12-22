package eu.crewbase.lineup.listener;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.OperationPeriod;
import eu.crewbase.lineup.exception.OperationPeriodNotFoundException;
import eu.crewbase.lineup.service.TimelineService;

@Component("lineup_AttendencePeriodListener")
public class AttendencePeriodListener
		implements BeforeInsertEntityListener<AttendencePeriod>, BeforeUpdateEntityListener<AttendencePeriod> {

	@Inject
	public TimelineService timelineDTOService;

	@Override
	public void onBeforeInsert(AttendencePeriod entity, EntityManager entityManager) {
		addOperationPeriod(entity);
	}

	@Override
	public void onBeforeUpdate(AttendencePeriod entity, EntityManager entityManager) {
		addOperationPeriod(entity);

	}

	private void addOperationPeriod(AttendencePeriod entity) {
		OperationPeriod operationPeriod = null;

		if (entity.getSite() != null && entity.getStartDate() != null && entity.getEndDate() != null) {
			try {
				operationPeriod = timelineDTOService.getOperationPeriod(entity.getSite(), entity.getStartDate(),
						entity.getEndDate());
				entity.setOperationPeriod(operationPeriod);
			} catch (OperationPeriodNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

}