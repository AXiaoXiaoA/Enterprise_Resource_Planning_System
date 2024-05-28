package com.changing.erpsystembackend.util;

public class DateConversionUtils {
    public static java.sql.Date toSqlDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    public static java.util.Date toUtilDate(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return new java.util.Date(date.getTime());
    }
}