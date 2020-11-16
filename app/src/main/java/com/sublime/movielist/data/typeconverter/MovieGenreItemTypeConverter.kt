package com.sublime.movielist.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sublime.movielist.model.Genre
import java.lang.reflect.Type

class MovieGenreItemTypeConverter {
    @TypeConverter
    fun fromGenreItemsList(showList: List<Genre?>?): String? {
        if (showList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.toJson(showList, type)
    }

    @TypeConverter
    fun toGenreItemsList(showString: String?): List<Genre>? {
        if (showString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson<List<Genre>>(showString, type)
    }
}
