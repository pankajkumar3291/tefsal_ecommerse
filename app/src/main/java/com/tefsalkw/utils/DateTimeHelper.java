package com.tefsalkw.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeHelper {

    public  static String getFormattedDate(String inputDateStr)
    {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("en"));
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("en"));

        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }


    public  static String getDeliveryDate(String inputDateStr,int days)
    {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("en"));
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("en"));

        Date date = null;
        Calendar c = Calendar.getInstance();
        try {
            date = inputFormat.parse(inputDateStr);


            c.setTime(date);
            c.add(Calendar.DATE, days);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(c.getTime());
    }


}
