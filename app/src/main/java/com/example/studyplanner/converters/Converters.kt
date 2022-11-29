package com.example.studyplanner.converters

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun dateFromLong(value: Long) : Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToLong(value: Date) : Long {
        return value.time
    }
}