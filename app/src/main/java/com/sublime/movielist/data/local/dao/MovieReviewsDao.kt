package com.sublime.movielist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sublime.movielist.model.MovieReviewsResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieReviewsDao {
    /**
     * Inserts [movieReviewsResponse] into the [MovieReviewsResponse.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param movieReviewsResponse MovieReviewsResponse
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieReviews(movieReviewsResponse: MovieReviewsResponse)

    /**
     * Deletes all movie reviews from the [MovieReviewsResponse.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${MovieReviewsResponse.TABLE_NAME}")
    fun deleteAllMovieReviews()

    /**
     * Fetches movie reviews from the [MovieReviewsResponse.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${MovieReviewsResponse.TABLE_NAME} WHERE movieId = :movieId")
    fun getMovieReviews(movieId: Int): Flow<MovieReviewsResponse>
}