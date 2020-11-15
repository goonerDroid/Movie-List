package com.sublime.movielist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sublime.movielist.data.local.dao.NowPlayingMovieDao
import com.sublime.movielist.model.NowPlayingMovie


/**
 * Abstract Movie List database.
 * It provides DAO [NowPlayingMovieDao] by using method [getNowPlayingMovieDao].
 */
@Database(
    entities = [NowPlayingMovie::class],
    version = DatabaseMigrations.DB_VERSION
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
}