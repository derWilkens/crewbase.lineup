package eu.crewbase.lineup.web.attendenceperiod;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.Datasource;

import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.OperationPeriod;
import eu.crewbase.lineup.exception.OperationNotFoundException;
import eu.crewbase.lineup.service.TimelineService;

public class AttendencePeriodEdit extends AbstractEditor<AttendencePeriod> {

	@Inject
	private TimelineService timelineDTOService;
	@Inject
	private Datasource<AttendencePeriod> attendencePeriodDs;
	
	@Override
	protected boolean preCommit() {
		AttendencePeriod attendencePeriod = attendencePeriodDs.getItem();
		OperationPeriod operationPeriod = null;
		try {
			operationPeriod = timelineDTOService.getOperationPeriod(attendencePeriod.getSite(),
					attendencePeriod.getStartDate(), attendencePeriod.getEndDate());
			attendencePeriod.setOperationPeriod(operationPeriod);

		} catch (OperationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.preCommit();
	}
	
}