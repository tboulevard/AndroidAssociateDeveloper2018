package com.boulevard.androidassociatedeveloper2018java.todolist;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 *
 * SQLite can only write specific data types into DB, we use type converter to input as Long,
 * and retrieve as a normal date object
 *
 */
public class DateConverter {

    // Used when READING from database
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    // Used when WRITING to database
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
