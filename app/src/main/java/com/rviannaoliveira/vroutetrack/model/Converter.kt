package com.rviannaoliveira.vroutetrack.model

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Criado por rodrigo on 20/05/17.
 */
class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}