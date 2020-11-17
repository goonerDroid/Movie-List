package com.sublime.movielist.di

import android.app.Application
import com.sublime.movielist.data.local.MovieListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class MovieListDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = MovieListDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideNowPlayingMoviesDao(database: MovieListDatabase) = database.getNowPlayingMovieDao()

    @Singleton
    @Provides
    fun provideMovieDetailDao(database: MovieListDatabase) = database.getMovieDetailDao()


    @Singleton
    @Provides
    fun provideMovieCreditsDao(database: MovieListDatabase) = database.getMovieCreditsDao()


    @Singleton
    @Provides
    fun provideMovieReviewsDao(database: MovieListDatabase) = database.getMovieReviewsDao()

    @Singleton
    @Provides
    fun provideSimilarMoviesDao(database: MovieListDatabase) = database.getSimilarMoviesDao()
}