package com.sublime.movielist.ui.movieDetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sublime.movielist.R
import com.sublime.movielist.databinding.ActivityMainBinding
import com.sublime.movielist.databinding.ActivityMovieDetailBinding
import com.sublime.movielist.ui.base.BaseActivity
import com.sublime.movielist.ui.movieList.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailActivity : BaseActivity<MovieListViewModel, ActivityMovieDetailBinding>() {

    override val mViewModel: MovieListViewModel
            by viewModels()

    override fun getViewBinding(): ActivityMovieDetailBinding  = ActivityMovieDetailBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
    }


}