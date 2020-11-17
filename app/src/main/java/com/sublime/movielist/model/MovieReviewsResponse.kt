package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.data.typeconverter.MovieReviewItemTypeConverter
import com.sublime.movielist.model.MovieReviewsResponse.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = TABLE_NAME)
data class MovieReviewsResponse (
    @PrimaryKey
    @SerializedName("id")
    var movieId: Int,
    @SerializedName("total_results")
    var movieReviewsCount: Int,

    @TypeConverters(MovieReviewItemTypeConverter::class)
    @SerializedName("results")
    var movieReviewsList: List<ReviewResult>,

): Parcelable {
    companion object {
        const val TABLE_NAME = "movie_reviews_data"
    }
}

