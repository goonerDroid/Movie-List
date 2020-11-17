package com.sublime.movielist.data.repository

import com.sublime.movielist.data.local.dao.*
import com.sublime.movielist.data.remote.MovieService
import com.sublime.movielist.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
@ExperimentalCoroutinesApi
@Singleton
class MoviesRepository  @Inject constructor(
    private val nowPlayingMovieDao: NowPlayingMovieDao,
    private val movieDetailDao: MovieDetailDao,
    private val movieCreditsDao: MovieCreditsDao,
    private val movieReviewsDao: MovieReviewsDao,
    private val similarMovieDao: SimilarMovieDao,
    private val movieService: MovieService
) {

    companion object {
        const val MOVIE_DB_API_KEY = "aec8954ba8890cabee9379419c88f6fc"
        const val MOVIE_REGION = "IN"
    }

    /**
     * Fetched the now playing movies from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    fun getNowPlayingMovies(): Flow<State<List<Movie>>> {
        return object : NetworkBoundRepository<List<Movie>, NowPlayingMovieResponse>() {

            override suspend fun saveRemoteData(response: NowPlayingMovieResponse) =
                nowPlayingMovieDao.insertNowPlayingMovies(response.results)

            override fun fetchFromLocal(): Flow<List<Movie>> = nowPlayingMovieDao.getAllNowPlayingMovies()

            override suspend fun fetchFromRemote(): Response<NowPlayingMovieResponse> = movieService.getNowPlayingMovies(MOVIE_DB_API_KEY/*,
                MOVIE_REGION*/)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getMovieDetail(movieId: Int): Flow<State<MovieDetail>> {
        return object : NetworkBoundRepository<MovieDetail, MovieDetail>() {

            override suspend fun saveRemoteData(response: MovieDetail) =
                    movieDetailDao.insertMovieDetail(response)

            override fun fetchFromLocal(): Flow<MovieDetail> = movieDetailDao.getMovieDetail(movieId)

            override suspend fun fetchFromRemote(): Response<MovieDetail> = movieService.getMovieDetail(movieId, MOVIE_DB_API_KEY)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getMovieCredits(movieId: Int): Flow<State<MovieCreditsResponse>> {
        return object : NetworkBoundRepository<MovieCreditsResponse, MovieCreditsResponse>() {

            override suspend fun saveRemoteData(response: MovieCreditsResponse) =
                movieCreditsDao.insertMovieCredits(response)

            override fun fetchFromLocal(): Flow<MovieCreditsResponse> = movieCreditsDao.getMovieCredits(movieId)

            override suspend fun fetchFromRemote(): Response<MovieCreditsResponse> = movieService.getMovieCreditsById(movieId, MOVIE_DB_API_KEY)
        }.asFlow().flowOn(Dispatchers.IO)
    }


    fun getMovieReviews(movieId: Int): Flow<State<MovieReviewsResponse>> {
        return object : NetworkBoundRepository<MovieReviewsResponse, MovieReviewsResponse>() {

            override suspend fun saveRemoteData(response: MovieReviewsResponse) =
                movieReviewsDao.insertMovieReviews(response)

            override fun fetchFromLocal(): Flow<MovieReviewsResponse> = movieReviewsDao.getMovieReviews(movieId)

            override suspend fun fetchFromRemote(): Response<MovieReviewsResponse> = movieService.getMovieReviewsById(movieId, MOVIE_DB_API_KEY)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getSimilarMoviesById(movieId: Int): Flow<State<List<SimilarMovie>>> {
        return object : NetworkBoundRepository<List<SimilarMovie>, SimilarMoviesResponse>() {

            override suspend fun saveRemoteData(response: SimilarMoviesResponse) =
                similarMovieDao.insertSimilarMovies(response.results)

            override fun fetchFromLocal(): Flow<List<SimilarMovie>> = similarMovieDao.getAllSimilarMovies()

            override suspend fun fetchFromRemote(): Response<SimilarMoviesResponse> = movieService.getSimilarMoviesById(movieId, MOVIE_DB_API_KEY)
        }.asFlow().flowOn(Dispatchers.IO)
    }


    fun getSearchedMovie(search: String): Flow<List<Movie>> {
        return nowPlayingMovieDao.getSearchedMovies(search) //Get searched movies from Room Database
                .flowOn(Dispatchers.Default)
    }
}