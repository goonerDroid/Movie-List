package com.sublime.movielist.ui.movieDetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sublime.movielist.data.repository.MoviesRepository
import com.sublime.movielist.model.*
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

    private val _movieDetailLiveData = MutableLiveData<State<MovieDetail>>()
    val movieDetailLiveData: LiveData<State<MovieDetail>>
        get() = _movieDetailLiveData


    private val _movieCreditsLiveData = MutableLiveData<State<MovieCreditsResponse>>()
    val movieCreditsLiveData: LiveData<State<MovieCreditsResponse>>
        get() = _movieCreditsLiveData

    private val _movieReviewsLiveData = MutableLiveData<State<MovieReviewsResponse>>()
    val movieReviewsLiveData: LiveData<State<MovieReviewsResponse>>
        get() = _movieReviewsLiveData

    private val _similarMoviesLiveData = MutableLiveData<State<List<SimilarMovie>>>()
    val similarMoviesLiveData: LiveData<State<List<SimilarMovie>>>
        get() = _similarMoviesLiveData

    private val movieIdIntentLiveData
            = savedStateHandle.getLiveData<Int>("id")
    val movieIdDataNotifier: LiveData<Int>
        get() = movieIdIntentLiveData



    fun getShowDetailFromId(showId: Int){
        viewModelScope.launch {
            moviesRepository.getMovieDetail(showId).collect {
                _movieDetailLiveData.value = it
            }
        }
    }

    fun getMovieCreditsFromId(showId: Int){
        viewModelScope.launch {
            moviesRepository.getMovieCredits(showId).collect {
                _movieCreditsLiveData.value = it
            }
        }
    }

    fun getMovieReviewsFromId(showId: Int){
        viewModelScope.launch {
            moviesRepository.getMovieReviews(showId).collect {
                _movieReviewsLiveData.value = it
            }
        }
    }


    fun getSimilarMoviesFromId(showId: Int){
        viewModelScope.launch {
            moviesRepository.getSimilarMoviesById(showId).collect {
                _similarMoviesLiveData.value = it
            }
        }
    }

}