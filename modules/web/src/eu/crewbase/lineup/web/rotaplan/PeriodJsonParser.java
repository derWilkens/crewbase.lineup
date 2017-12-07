package eu.crewbase.lineup.web.rotaplan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import elemental.json.JsonObject;
import eu.crewbase.lineup.Utils;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.period.OperationPeriod;
import eu.crewbase.lineup.entity.period.PeriodJsonDTO;
import eu.crewbase.lineup.service.EntityService;
import eu.crewbase.lineup.service.TimelineService;

public class PeriodJsonParser {
	
	@Inject
	private EntityService entityService;
	@Inject
	private TimelineService timelineDTOService;
	

	public PeriodJsonDTO parse(JsonObject jsonItem) {
		PeriodJsonDTO newItem = new PeriodJsonDTO();
		if (jsonItem.hasKey("start")) {
			try {
				newItem.setStartDate(jsonDateToDateWoTime(jsonItem.getString("start")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			newItem.setStartDate(Utils.clearDate(new Date()));
			newItem.setItemIncomplete(true);
		}
		int duration = 1; // entweder das gelieferte Endedatum nehmen,
		// oder die Duration oder 1
		if (jsonItem.hasKey("end")) {
			try {
				newItem.setEndDate(jsonDateToDate(jsonItem.getString("end")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (jsonItem.hasKey("duration") && !jsonItem.getString("duration").equals("null")) {
			duration = Integer.parseInt(jsonItem.getString("duration"));
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(newItem.getStartDate());
		c.add(Calendar.DAY_OF_YEAR, duration);
		newItem.setEndDate(c.getTime());
		
		if (jsonItem.hasKey("userId")) {
			newItem.setPersonOnDuty(
					entityService.getById(AppUser.class, UUID.fromString(jsonItem.getString("userId"))));
		} else {
			newItem.setItemIncomplete(true);
		}
		
		if (jsonItem.hasKey("siteId") && !jsonItem.getString("siteId").equals("null")) {

			UUID siteId = UUID.fromString(jsonItem.getString("siteId"));
			OperationPeriod operationPeriod = timelineDTOService.getOperationPeriod(siteId, newItem.getStartDate(),
					newItem.getEndDate());

			newItem.setOperationPeriod(operationPeriod);
		} else {
			// itemIncomplete = true; nicht jeder Dienst braucht eine Site,
			// wenn doch kann manuell nachgepflegt werden.
		}
		return newItem;
	}

	
	private Date jsonDateToDate(String rawDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		return formatter.parse(rawDate);
	}
	
	private Date jsonDateToDateWoTime(String rawDate) throws ParseException {
		return dateToDateWoTime(jsonDateToDate(rawDate));
	}

	private Date dateToDateWoTime(Date date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.parse(formatter.format(date));
	}



}
