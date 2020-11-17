package com.sublime.movielist.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sublime.movielist.model.ReviewResult
import java.lang.reflect.Type

class MovieReviewItemTypeConverter {
    @TypeConverter
    fun fromMovieReviewItemsList(showList: List<ReviewResult?>?): String? {
        if (showList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ReviewResult?>?>() {}.type
        return gson.toJson(showList, type)
    }

    @TypeConverter
    fun toMovieReviewItemsList(showString: String?): List<ReviewResult>? {
        if (showString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ReviewResult?>?>() {}.type
        return gson.fromJson<List<ReviewResult>>(showString, type)
    }
}