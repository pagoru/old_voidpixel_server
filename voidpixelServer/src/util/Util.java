package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
	
	public static String getTime() {
    	Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	return sdf.format(cal.getTime());
    }

}
