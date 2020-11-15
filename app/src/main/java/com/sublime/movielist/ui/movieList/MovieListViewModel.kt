package com.sublime.movielist.ui.movieList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sublime.movielist.data.repository.MoviesRepository
import com.sublime.movielist.model.NowPlayingMovie
import com.sublime.movielist.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for [MovieListActivity]
 */
@ExperimentalCoroutinesApi
class MovieListViewModel @ViewModelInject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _nowPlayingMoviesLiveData = MutableLiveData<State<List<NowPlayingMovie>>>()

    val nowPlayingMoviesLiveData: LiveData<State<List<NowPlayingMovie>>>
        get() = _nowPlayingMoviesLiveData

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            moviesRepository.getNowPlayingMovies().collect {
                _nowPlayingMoviesLiveData.value =it
            }

        }
    }

}