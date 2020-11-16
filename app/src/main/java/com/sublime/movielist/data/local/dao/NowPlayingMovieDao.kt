package com.sublime.movielist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sublime.movielist.model.NowPlayingMovie
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [com.sublime.movielist.data.local.dao.MovieListDatabase]
 */
@Dao
interface NowPlayingMovieDao {

    /**
     * Inserts [nowPlayingMovie] into the [NowPlayingMovie.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param nowPlayingMovie List<NowPlayingMovie>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowPlayingMovies(nowPlayingMovie: List<NowPlayingMovie>)

    /**
     * Deletes all now playing movies from the [NowPlayingMovie.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${NowPlayingMovie.TABLE_NAME}")
    fun deleteAllNowPlayingMovies()

    /**
     * Fetches all now playing movies from the [NowPlayingMovie.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${NowPlayingMovie.TABLE_NAME}")
    fun getAllNowPlayingMovies(): Flow<List<NowPlayingMovie>>


    @Query("SELECT * FROM ${NowPlayingMovie.TABLE_NAME} WHERE movieTitle LIKE '%' || :search || '%'")
    fun getSearchedMovies(search: String?): Flow<List<NowPlayingMovie>>
}