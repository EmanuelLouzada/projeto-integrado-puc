package com.tees.checklist.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private static final long TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    public static long getUTCTicks(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return (calendar.getTimeInMillis() * TICKS_PER_MILLISECOND) + TICKS_AT_EPOCH;

    }

    public static Date getDate(long UTCTicks){

        return new Date((UTCTicks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND);

    }
    public static String getFormattedDate(String format, Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String formattedDate = formatter.format(date);
        return formattedDate;
    }


    public static Date getDateFromString(String format, String dateText) {
        try {
            SimpleDateFormat dt = new SimpleDateFormat(format);
            Date date = dt.parse(dateText);

            return date;
        } catch (Exception e) {
            return new Date();
        }
    }

    public static boolean isValidDate(String inDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }


    public static String formataDataMMYYYY(Date date)
    {
        String[] mes = { "", "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" };
        return mes[date.getMonth()] + "/" + date.getYear();
    }


    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }
    public static Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();
    }


}