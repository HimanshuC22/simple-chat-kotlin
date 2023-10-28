package com.example.simplechat.data.storage.converters

import androidx.room.TypeConverter
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.time.Instant

class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}