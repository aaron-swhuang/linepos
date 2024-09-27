package com.house.linepos.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date


class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(tags: List<Int>?): String? {
        return tags?.let{ gson.toJson(it) }
    }

    @TypeConverter
    fun fromString(tagsStr: String?): List<Int>? {
        return tagsStr?.let {
            val type = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(tagsStr, type)
        }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}