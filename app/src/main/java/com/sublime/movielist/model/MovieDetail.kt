package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.data.typeconverter.MovieGenreItemTypeConverter
import com.sublime.movielist.model.MovieDetail.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = TABLE_NAME)
data class MovieDetail (

    @SerializedName("adult") var isAdultMovie: Boolean,
    @SerializedName("backdrop_path") var movieBackdropImg: String,

    @TypeConverters(MovieGenreItemTypeConverter::class)
    @SerializedName("genres") var movieGenreList: List<Genre>,

    @SerializedName("homepage") var movieHomepageLink: String,

    @PrimaryKey
    @SerializedName("id") var movieId: Int,
    @SerializedName("title") var movieTitle: String,
    @SerializedName("overview") var movieOverview: String,
    @SerializedName("poster_path") var moviePosterImg: String,
    @SerializedName("release_date") var movieReleaseDate: String,
    @SerializedName("runtime") var movieRuntime: String,
    @SerializedName("vote_count") var movieVoteCount: Int,

    ): Parcelable {
        companion object {
            const val TABLE_NAME = "movie_detail_data"
        }
    }

