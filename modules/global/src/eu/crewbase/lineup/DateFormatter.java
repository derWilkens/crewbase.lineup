package eu.crewbase.lineup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	public static String toMMJJJJ(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM.JJJJ");
		return dateFormatter.format(date);

	}

	public static String toDDMMJJJJ(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.YYYY");
		return dateFormatter.format(date);

	}

	public static Date jsonDateToDate(String rawDate){
		DateFormat formatter = null;
		if(rawDate==null)return null;
		if(rawDate.length() == 10) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		}
		try {
			return formatter.parse(rawDate);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Date jsonDateToDateWoTime(String rawDate){
		try {
			return dateToDateWoTime(jsonDateToDate(rawDate));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Date dateToDateWoTime(Date date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.parse(formatter.format(date));
	}
}
