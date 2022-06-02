package com.tees.checklist.data.converter;

import androidx.room.TypeConverter;

import com.tees.checklist.commons.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(String timestamp) {
        try {
            return timestamp == null ? null : new SimpleDateFormat(Constants.FORMAT_DATE_TIME_DB_MS).parse(timestamp);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String toString(Date date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE_TIME_DB_MS);
            String strDate = dateFormat.format(date);
            return strDate == null ? null : strDate;
        } catch (Exception e) {
            return null;
        }
    }
}