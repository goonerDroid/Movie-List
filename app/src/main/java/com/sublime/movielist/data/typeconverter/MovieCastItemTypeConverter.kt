package com.sublime.movielist.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sublime.movielist.model.MovieCast
import java.lang.reflect.Type

class MovieCastItemTypeConverter {

    @TypeConverter
    fun fromMovieCastItemsList(showList: List<MovieCast?>?): String? {
        if (showList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieCast?>?>() {}.type
        return gson.toJson(showList, type)
    }

    @TypeConverter
    fun toMovieCastItemsList(showString: String?): List<MovieCast>? {
        if (showString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieCast?>?>() {}.type
        return gson.fromJson<List<MovieCast>>(showString, type)
    }
}