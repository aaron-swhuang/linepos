package com.house.linepos.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntListToStringList(tags: List<Int>?): String? {
        return tags?.let{ gson.toJson(it) }
    }

    @TypeConverter
    fun fromStringListToIntList(tagsStr: String?): List<Int>? {
        return tagsStr?.let {
            val type = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(tagsStr, type)
        }
    }
}