package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.data.typeconverter.MovieCastItemTypeConverter
import com.sublime.movielist.model.MovieCreditsResponse.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize


@Keep
@Parcelize
@Entity(tableName = TABLE_NAME)
data class MovieCreditsResponse (

    @PrimaryKey
    @SerializedName("id")
    var movieId: Int,

    @TypeConverters(MovieCastItemTypeConverter::class)
    @SerializedName("cast")
    val movieCastList: List<MovieCast>,

    @TypeConverters(MovieCastItemTypeConverter::class)
    @SerializedName("crew")
    val movieCrewList: List<MovieCast>
):Parcelable {
    companion object {
        const val TABLE_NAME = "movie_credits_data"
    }
}
