package com.sublime.movielist.ui.movieList

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.sublime.movielist.R
import com.sublime.movielist.databinding.ActivityMovieListBinding
import com.sublime.movielist.model.Movie
import com.sublime.movielist.model.State
import com.sublime.movielist.ui.base.BaseActivity
import com.sublime.movielist.ui.movieDetail.MovieDetailActivity
import com.sublime.movielist.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieListActivity : BaseActivity<MovieListViewModel, ActivityMovieListBinding>() {
    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

    private var mAdapter: MovieListAdapter = MovieListAdapter(this::onItemClicked)
    override val mViewModel: MovieListViewModel by viewModels()
    override fun getViewBinding(): ActivityMovieListBinding = ActivityMovieListBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        // Initialize RecyclerView
        mViewBinding.nowPlayingMovieRecyclerView.layoutManager = GridLayoutManager(this,2)
        mViewBinding.nowPlayingMovieRecyclerView.adapter = mAdapter

        initNowPlayingMoviesObservers()
        handleNetworkChanges()
        initSearchViewListener()
    }

    private fun initSearchViewListener() {
        mViewBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { mViewModel.setSearchQuery(it) }
                return true
            }
        }
        )
    }

    private fun initNowPlayingMoviesObservers() {
        mViewModel.moviesLiveData.observe(
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
        if (mViewModel.moviesLiveData.value !is State.Success) {
            getNowPlayingMovies()
        }

        mViewModel.searchedMovieLiveData.observe(this, Observer {
            mAdapter.submitList(it.toMutableList())
        })
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
                        if (mViewModel.moviesLiveData.value is State.Error || mAdapter.itemCount == 0) {
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

    private fun onItemClicked(movie: Movie, imageView: ImageView) {
//        val intent = Intent(this, PostDetailsActivity::class.java)
//        intent.putExtra(PostDetailsActivity.POST_ID, nowPlayingMovie.id)
//
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            imageView,
//            imageView.transitionName
//        )
//
//        startActivity(intent, options.toBundle())

        startActivity(
                Intent(this, MovieDetailActivity::class.java).apply {
                    putExtra("id", movie.movieId)
                }
        )
    }
}