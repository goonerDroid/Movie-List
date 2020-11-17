package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.model.ReviewResult.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = TABLE_NAME)
data class ReviewResult (
    @PrimaryKey
    @SerializedName("id")
    var movieReviewId: String,
    @SerializedName("author")
    var movieReviewAuthor: String,
    @SerializedName("content")
    var movieReviewContent: String
): Parcelable {
    companion object {
        const val TABLE_NAME = "movie_reviews_results_data"
    }
}
