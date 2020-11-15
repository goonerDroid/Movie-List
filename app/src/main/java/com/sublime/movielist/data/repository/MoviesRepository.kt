package com.sublime.movielist.data.repository

import com.sublime.movielist.data.local.dao.NowPlayingMovieDao
import com.sublime.movielist.data.remote.MovieService
import com.sublime.movielist.model.NowPlayingMovie
import com.sublime.movielist.model.NowPlayingMovieResponse
import com.sublime.movielist.model.State
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
    private val movieService: MovieService
) {

    companion object {
        const val MOVIE_DB_API_KEY = "aec8954ba8890cabee9379419c88f6fc"
        const val MOVIE_REGION = "IN"
    }

    /**
     * Fetched the posts from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    fun getNowPlayingMovies(): Flow<State<List<NowPlayingMovie>>> {
        return object : NetworkBoundRepository<List<NowPlayingMovie>, NowPlayingMovieResponse>() {

            override suspend fun saveRemoteData(response: NowPlayingMovieResponse) =
                nowPlayingMovieDao.insertNowPlayingMovies(response.results)

            override fun fetchFromLocal(): Flow<List<NowPlayingMovie>> = nowPlayingMovieDao.getAllNowPlayingMovies()

            override suspend fun fetchFromRemote(): Response<NowPlayingMovieResponse> = movieService.getNowPlayingMovies(MOVIE_DB_API_KEY/*,
                MOVIE_REGION*/)
        }.asFlow().flowOn(Dispatchers.IO)
    }
}