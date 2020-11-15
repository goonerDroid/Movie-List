package com.sublime.movielist.ui.movieList

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sublime.movielist.R
import com.sublime.movielist.databinding.ActivityMainBinding
import com.sublime.movielist.model.NowPlayingMovie
import com.sublime.movielist.model.State
import com.sublime.movielist.ui.base.BaseActivity
import com.sublime.movielist.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieListActivity : BaseActivity<MovieListViewModel, ActivityMainBinding>() {
    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

    private var mAdapter: MovieListAdapter = MovieListAdapter(this::onItemClicked)
    override val mViewModel: MovieListViewModel by viewModels()
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        // Initialize RecyclerView

//        mViewBinding.nowPlayingMovieRecyclerView.adapter = mAdapter
//        mViewBinding.nowPlayingMovieRecyclerView.apply {
//            layoutManager = LinearLayoutManager(this@MovieListActivity)
//        }

        mViewBinding.nowPlayingMovieRecyclerView.layoutManager = GridLayoutManager(this,2)
        mViewBinding.nowPlayingMovieRecyclerView.adapter = mAdapter
        initNowPlayingMovies()
        handleNetworkChanges()
    }

    private fun initNowPlayingMovies() {
        mViewModel.nowPlayingMoviesLiveData.observe(
            this,
            Observer { state ->
                when (state) {
                    is State.Loading -> showLoading(true)
                    is State.Success -> {
                        if (state.data.isNotEmpty()) {
                            mAdapter.submitList(state.data.toMutableList())
                            showLoading(false)
                        }
                    }
                    is State.Error -> {
                        showToast(state.message)
                        showLoading(false)
                    }
                }
            }
        )

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getNowPlayingMovies()
        }

        // If State isn't `Success` then again reload movies.
        if (mViewModel.nowPlayingMoviesLiveData.value !is State.Success) {
            getNowPlayingMovies()
        }
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(
            this,
            Observer { isConnected ->
                if (!isConnected) {
                    mViewBinding.textViewNetworkStatus.text =
                        getString(R.string.text_no_connectivity)
                    mViewBinding.networkStatusLayout.apply {
                        show()
                        setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                    }
                } else {
                    if (mViewModel.nowPlayingMoviesLiveData.value is State.Error || mAdapter.itemCount == 0) {
                        getNowPlayingMovies()
                    }
                    mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                    mViewBinding.networkStatusLayout.apply {
                        setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                        animate()
                            .alpha(1f)
                            .setStartDelay(ANIMATION_DURATION)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    hide()
                                }
                            }
                            )
                    }
                }
            }
        )
    }

    private fun getNowPlayingMovies() {
        mViewModel.getNowPlayingMovies()
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun onItemClicked(nowPlayingMovie: NowPlayingMovie, imageView: ImageView) {
//        val intent = Intent(this, PostDetailsActivity::class.java)
//        intent.putExtra(PostDetailsActivity.POST_ID, nowPlayingMovie.id)
//
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            imageView,
//            imageView.transitionName
//        )
//
//        startActivity(intent, options.toBundle())//TODO
    }





}