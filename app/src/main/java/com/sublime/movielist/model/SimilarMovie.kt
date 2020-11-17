package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.model.SimilarMovie.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

/**
 * Data class for Database entity and Serialization for Similar movies.
 */
@Keep
@Parcelize
@Entity(tableName = TABLE_NAME)
data class SimilarMovie (
    @PrimaryKey
    @SerializedName("id") var movieId: Int,
    @SerializedName("title") var movieTitle: String,
    @Nullable
    @SerializedName("poster_path") var moviePosterPath: String? = "",
    @Nullable
    @SerializedName("backdrop_path") var movieBackdropPath: String? = "",
    @SerializedName("release_date") var movieReleaseDate: String,
    @SerializedName("vote_count") var movieVoteCount: Int
): Parcelable {
    companion object {
        const val TABLE_NAME = "similar_movies_data"
    }
}
