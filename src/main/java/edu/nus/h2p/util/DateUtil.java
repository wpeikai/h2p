package edu.nus.h2p.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

public class DateUtil {
    private static final Logger logger = Logger.getLogger(DateUtil.class.getName());
    private static final DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

    public static final long MINUTE_IN_MINI_SECOND = 60000;
    public static Date parseDateByFormat(String dateStr){
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            logger.severe("dateStr " + dateStr + " parse to format: " + format.toString() + " failed");
        }
        return date;
    }

    public static Date addTime(Date date, long dateToAdd){
        return new Date(date.getTime() + dateToAdd);
    }
}
