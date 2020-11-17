package com.sublime.movielist.data.remote

import com.sublime.movielist.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Service to fetch movies using the movieDB end point [MOVIE_DB_API_URL].
 */
interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") api_key: String/*,
                                    @Query("region") region: String*/): Response<NowPlayingMovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int,
                               @Query("api_key") api_key: String): Response<MovieDetail>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCreditsById(@Path("movie_id") movieId: Int,
                                    @Query("api_key") api_key: String): Response<MovieCreditsResponse>


    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviewsById(@Path("movie_id") movieId: Int,
                                    @Query("api_key") api_key: String): Response<MovieReviewsResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMoviesById(@Path("movie_id") movieId: Int,
                                    @Query("api_key") api_key: String): Response<SimilarMoviesResponse>

    companion object {
        const val MOVIE_DB_API_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}