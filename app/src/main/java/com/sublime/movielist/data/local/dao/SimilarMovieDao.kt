package com.sublime.movielist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sublime.movielist.model.Movie
import com.sublime.movielist.model.SimilarMovie
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [com.sublime.movielist.data.local.dao.MovieListDatabase]
 */
@Dao
interface SimilarMovieDao {
    /**
     * Inserts [similarMovie] into the [SimilarMovie.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param similarMovie List<SimilarMovie>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSimilarMovies(similarMovie: List<SimilarMovie>)

    /**
     * Deletes all similar movies from the [SimilarMovie.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${SimilarMovie.TABLE_NAME}")
    fun deleteAllSimilarMovies()

    /**
     * Fetches all similar movies from the [SimilarMovie.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${SimilarMovie.TABLE_NAME}")
    fun getAllSimilarMovies(): Flow<List<SimilarMovie>>
}