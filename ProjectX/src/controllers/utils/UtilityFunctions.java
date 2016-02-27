package controllers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityFunctions {
	public static boolean isValidDateFormat(String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				return false;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
			// TODO Log the exception
			return false;
		}

		return true;
	}

	public static boolean isValidMail(String email) {
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

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
