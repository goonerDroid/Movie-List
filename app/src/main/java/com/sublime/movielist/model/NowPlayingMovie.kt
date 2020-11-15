package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.model.NowPlayingMovie.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

/**
 * Data class for Database entity and Serialization.
 */
@Keep
@Parcelize
@Entity(tableName = TABLE_NAME)
data class NowPlayingMovie (

    @PrimaryKey
    @SerializedName("id") var movieId: Int,
    @SerializedName("title") var movieTitle: String,
    @Nullable
    @SerializedName("poster_path") var moviePosterPath: String? = "",
    @SerializedName("release_date") var movieReleaseDate: String,
    @SerializedName("vote_count") var movieVoteCount: Int

): Parcelable {
    companion object {
        const val TABLE_NAME = "shows_data"
    }
}