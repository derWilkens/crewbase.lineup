package eu.crewbase.lineup;

import java.text.SimpleDateFormat;
import java.util.Date;



public class DateFormatter {
	
	public static String toMMJJJJ(Date date){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM.JJJJ");
		return dateFormatter.format(date);
		
	}
	public static String toDDMMJJJJ(Date date){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("DD.MM.JJJJ");
		return dateFormatter.format(date);
		
	}
}
