package com.sublime.movielist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sublime.movielist.model.MovieCreditsResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieCreditsDao {
    /**
     * Inserts [movieCreditsResponse] into the [MovieCreditsResponse.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param movieCreditsResponse MovieCreditsResponse
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieCredits(movieCreditsResponse: MovieCreditsResponse)

    /**
     * Deletes all movie credits from the [MovieCreditsResponse.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${MovieCreditsResponse.TABLE_NAME}")
    fun deleteAllMovieCredits()

    /**
     * Fetches movie credits from the [MovieCreditsResponse.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${MovieCreditsResponse.TABLE_NAME} WHERE movieId = :movieId")
    fun getMovieCredits(movieId: Int): Flow<MovieCreditsResponse>
}