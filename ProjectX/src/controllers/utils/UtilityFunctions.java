package controllers.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UtilityFunctions {

	public static Date calculateMin(Date d1, Date d2) {
		if (d1.before(d2)) {
			return d1;
		}
		return d2;
	}

	public static Date calculateMax(Date d1, Date d2) {
		if (d1.after(d2)) {
			return d1;
		}
		return d2;
	}

	public static long getDifferenceDaysExclusive(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}

	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = getDifferenceDaysExclusive(d1, d2);
		if (diff == 0) {
			return diff;
		}
		return 1 + diff;
	}
	
	
}
