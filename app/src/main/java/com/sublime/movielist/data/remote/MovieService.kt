package com.sublime.movielist.data.remote

import com.sublime.movielist.model.NowPlayingMovie
import com.sublime.movielist.model.NowPlayingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service to fetch movies using the movieDB end point [MOVIE_DB_API_URL].
 */
interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") api_key: String/*,
                                    @Query("region") region: String*/): Response<NowPlayingMovieResponse>

    companion object {
        const val MOVIE_DB_API_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}