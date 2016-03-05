package controllers.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.GetWorkhoursProperties;

public class UtilityFunctions {

    private static final Logger LOGGER = Logger.getLogger(UtilityFunctions.class.getName());

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private UtilityFunctions() {};

    public static long getHoursRequestedTask(String startDay, String finishDay) {
        long hourRequested = 0;
        try {
            Integer[] propertiesValues = GetWorkhoursProperties.getInstance().getPropValues();
            Date start = sdf.parse(startDay);
            Date finish = sdf.parse(finishDay);
            hourRequested = propertiesValues[0] * UtilityFunctions.getDifferenceDays(start, finish);

        } catch (ParseException | IOException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
        }
        return hourRequested;
    }

    public static boolean isValidDateFormat(String value) {
        Date date = null;
        try {

            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                return false;
            }
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", ex);
            return false;
        }

        return true;
    }

    public static boolean isValidMail(String email) {
        final Pattern VALIDEMAILADDRESSREGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALIDEMAILADDRESSREGEX.matcher(email);
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

    public static String getCurrentDateTime() {
        /* get current date time with Calendar() */
        Date date = new Date();
        return sdf.format(date);

    }

}
