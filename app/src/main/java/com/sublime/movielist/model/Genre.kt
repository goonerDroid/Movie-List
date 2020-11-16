package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.model.Genre.Companion.GENRE_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = GENRE_TABLE_NAME)
data class Genre (
        @PrimaryKey
        @SerializedName("id") var genreId: Int,
        @SerializedName("name") var genreName: String

): Parcelable {

    companion object {
        const val GENRE_TABLE_NAME = "genre_data"
    }

}