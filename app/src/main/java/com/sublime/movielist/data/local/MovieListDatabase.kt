package com.sublime.movielist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sublime.movielist.data.local.dao.*
import com.sublime.movielist.data.typeconverter.MovieCastItemTypeConverter
import com.sublime.movielist.data.typeconverter.MovieGenreItemTypeConverter
import com.sublime.movielist.data.typeconverter.MovieReviewItemTypeConverter
import com.sublime.movielist.model.*


/**
 * Abstract Movie List database.
 * It provides DAO [NowPlayingMovieDao] by using method [getNowPlayingMovieDao].
 */
@Database(
    entities = [Movie::class, MovieDetail::class,Genre::class, MovieCreditsResponse::class, MovieCast::class,
    MovieReviewsResponse::class,SimilarMovie::class],
    version = DatabaseMigrations.DB_VERSION
)
@TypeConverters(MovieGenreItemTypeConverter::class,MovieCastItemTypeConverter::class,
    MovieReviewItemTypeConverter::class
)
abstract class MovieListDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "movie_list_database"

        @Volatile
        private var INSTANCE: MovieListDatabase? = null

        fun getInstance(context: Context): MovieListDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieListDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

    /**
     * @return [NowPlayingMovieDao] Now Playing Movies Data Access Object.
     */
    abstract fun getNowPlayingMovieDao(): NowPlayingMovieDao

    /**
     * @return [MovieDetailDao] Movie Detail Data Access Object.
     */
    abstract fun getMovieDetailDao(): MovieDetailDao

    /**
     * @return [MovieCreditsDao] Movie Detail Data Access Object.
     */
    abstract fun getMovieCreditsDao(): MovieCreditsDao

    /**
     * @return [MovieReviewsDao] Movie Detail Data Access Object.
     */
    abstract fun getMovieReviewsDao(): MovieReviewsDao

    /**
     * @return [SimilarMovieDao] Movie Detail Data Access Object.
     */
    abstract fun getSimilarMoviesDao(): SimilarMovieDao


}