package eu.crewbase.lineup;

import java.util.Date;

public class Utils {

	public static Date clearDate(Date dateWithTime){
		long millisInDay = 60 * 60 * 24 * 1000;
		long currentTime = dateWithTime.getTime();
		long dateOnly = (currentTime / millisInDay) * millisInDay;
		return new Date(dateOnly);
	}
}
