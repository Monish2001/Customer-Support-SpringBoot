package com.example.CustomerSupport.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static Timestamp getCurrentTimeStamp()
    {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
