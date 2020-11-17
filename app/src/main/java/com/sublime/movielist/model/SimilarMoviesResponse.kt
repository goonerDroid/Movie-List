package com.sublime.movielist.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SimilarMoviesResponse (
    @SerializedName("results")
    val results: ArrayList<SimilarMovie> = ArrayList()
)
