package com.sublime.movielist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sublime.movielist.data.local.dao.MovieDetailDao
import com.sublime.movielist.data.local.dao.NowPlayingMovieDao
import com.sublime.movielist.data.typeconverter.MovieGenreItemTypeConverter
import com.sublime.movielist.model.Genre
import com.sublime.movielist.model.MovieDetail
import com.sublime.movielist.model.NowPlayingMovie


/**
 * Abstract Movie List database.
 * It provides DAO [NowPlayingMovieDao] by using method [getNowPlayingMovieDao].
 */
@Database(
    entities = [NowPlayingMovie::class, MovieDetail::class,Genre::class],
    version = DatabaseMigrations.DB_VERSION
)
@TypeConverters(MovieGenreItemTypeConverter::class)
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


}