package com.sublime.movielist.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NowPlayingMovieResponse(
    @SerializedName("results")
    val results: ArrayList<NowPlayingMovie> = ArrayList()
)