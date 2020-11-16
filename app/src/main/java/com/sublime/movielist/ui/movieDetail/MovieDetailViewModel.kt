package com.sublime.movielist.ui.movieDetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sublime.movielist.data.repository.MoviesRepository
import com.sublime.movielist.model.MovieDetail
import com.sublime.movielist.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for [MovieDetailActivity]
 */
@ExperimentalCoroutinesApi
class MovieDetailViewModel  @ViewModelInject constructor(private val moviesRepository: MoviesRepository,
                                                         @Assisted private val savedStateHandle: SavedStateHandle) :
        ViewModel() {

    private val _showDetailLiveData = MutableLiveData<State<MovieDetail>>()

    val showDetailLiveData: LiveData<State<MovieDetail>>
        get() = _showDetailLiveData

    private val movieIdIntentLiveData
            = savedStateHandle.getLiveData<Int>("id")

    val movieIdDataNotifier: LiveData<Int>
        get() = movieIdIntentLiveData

    fun getShowDetailFromId(showId: Int){
        viewModelScope.launch {
            moviesRepository.getMovieDetail(showId).collect {
                _showDetailLiveData.value = it
            }
        }
    }

}