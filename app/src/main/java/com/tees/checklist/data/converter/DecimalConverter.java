package com.tees.checklist.data.converter;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class DecimalConverter {

    @TypeConverter
    public static BigDecimal toBigDecimal(String decimal) {
        return decimal == null ? null : new BigDecimal(decimal);
    }

    @TypeConverter
    public static String toString(BigDecimal decimal) {
        return decimal == null ? null :decimal.toPlainString();
    }
}