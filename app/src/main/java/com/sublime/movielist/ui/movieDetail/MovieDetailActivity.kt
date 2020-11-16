package com.sublime.movielist.ui.movieDetail

import android.os.Bundle
import androidx.activity.viewModels
import com.sublime.movielist.databinding.ActivityMovieDetailBinding
import com.sublime.movielist.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailActivity : BaseActivity<MovieDetailViewModel, ActivityMovieDetailBinding>() {

    override val mViewModel: MovieDetailViewModel
            by viewModels()

    override fun getViewBinding(): ActivityMovieDetailBinding  = ActivityMovieDetailBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
    }


}