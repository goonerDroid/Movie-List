package com.sublime.movielist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sublime.movielist.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [com.sublime.movielist.data.local.dao.MovieListDatabase]
 */
@Dao
interface NowPlayingMovieDao {

    /**
     * Inserts [movie] into the [Movie.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param movie List<NowPlayingMovie>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowPlayingMovies(movie: List<Movie>)

    /**
     * Deletes all now playing movies from the [Movie.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    fun deleteAllNowPlayingMovies()

    /**
     * Fetches all now playing movies from the [Movie.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun getAllNowPlayingMovies(): Flow<List<Movie>>


    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE movieTitle LIKE '%' || :search || '%'")
    fun getSearchedMovies(search: String?): Flow<List<Movie>>
}