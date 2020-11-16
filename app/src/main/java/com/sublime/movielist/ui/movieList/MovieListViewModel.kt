package com.sublime.movielist.ui.movieList

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sublime.movielist.data.repository.MoviesRepository
import com.sublime.movielist.model.NowPlayingMovie
import com.sublime.movielist.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * ViewModel for [MovieListActivity]
 */
@FlowPreview
@ExperimentalCoroutinesApi
class MovieListViewModel @ViewModelInject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _nowPlayingMoviesLiveData = MutableLiveData<State<List<NowPlayingMovie>>>()

    val nowPlayingMoviesLiveData: LiveData<State<List<NowPlayingMovie>>>
        get() = _nowPlayingMoviesLiveData


    private val searchChanel = ConflatedBroadcastChannel<String>()

    val searchedMovieLiveData = searchChanel.asFlow()
            .flatMapLatest { search ->
                moviesRepository.getSearchedDogs(search)
            }
            .catch { throwable ->
                //Log exception here
            }.asLiveData()

    fun setSearchQuery(search: String) {
        searchChanel.offer(search)
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            moviesRepository.getNowPlayingMovies().collect {
                _nowPlayingMoviesLiveData.value =it
            }

        }
    }

}