package com.sublime.movielist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sublime.movielist.model.MovieDetail
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [com.sublime.movielist.data.local.dao.MovieListDatabase]
 */
@Dao
interface MovieDetailDao {

    /**
     * Inserts [movieDetail] into the [MovieDetail.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param movieDetail List<NowPlayingMovie>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieDetail: MovieDetail)

    /**
     * Deletes all now playing movies from the [MovieDetail.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${MovieDetail.TABLE_NAME}")
    fun deleteAllNowPlayingMovies()

    /**
     * Fetches all now playing movies from the [MovieDetail.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${MovieDetail.TABLE_NAME} WHERE movieId = :movieId")
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
}