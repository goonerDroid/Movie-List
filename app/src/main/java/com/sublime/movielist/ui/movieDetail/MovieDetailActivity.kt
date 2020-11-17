package com.sublime.movielist.ui.movieDetail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sublime.movielist.R
import com.sublime.movielist.data.remote.MovieService
import com.sublime.movielist.databinding.ActivityMovieDetailBinding
import com.sublime.movielist.model.*
import com.sublime.movielist.ui.base.BaseActivity
import com.sublime.movielist.ui.movieList.MovieListActivity
import com.sublime.movielist.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailActivity : BaseActivity<MovieDetailViewModel, ActivityMovieDetailBinding>() {

    private val originalDateFormat =  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val customDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    private var movieId: Int? = null
    private var mMovieCastAdapter: MovieCreditsListAdapter = MovieCreditsListAdapter()
    private var mMovieCrewAdapter: MovieCreditsListAdapter = MovieCreditsListAdapter()
    private var mMovieReviewsAdapter: MovieReviewsListAdapter = MovieReviewsListAdapter()
    private var mSimilarMoviesAdapter: SimilarMoviesListAdapter = SimilarMoviesListAdapter()
    override val mViewModel: MovieDetailViewModel
            by viewModels()

    override fun getViewBinding(): ActivityMovieDetailBinding  = ActivityMovieDetailBinding.inflate(layoutInflater)
    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        initToolbar()
        movieId = mViewModel.movieIdDataNotifier.value
        initRecyclerViews()
        handleNetworkChanges()
    }

    override fun onResume() {
        super.onResume()
        initMovieDetailObservers()
    }

    private fun initMovieDetailObservers() {
        mViewModel.movieDetailLiveData.observe(
                this,
                { state ->
                    when (state) {
                        is State.Loading -> showLoading(true)
                        is State.Success -> {
                            setupMovieSynopsisUi(state.data)
                            showLoading(false)
                        }
                        is State.Error -> {
                            showToast(state.message)
                            showLoading(false)
                        }
                    }
                }
        )

        mViewModel.movieCreditsLiveData.observe(this,
                { state ->
                    when (state) {
                        is State.Loading -> showLoading(true)
                        is State.Success -> {
                            setupMovieCreditsUi(state.data)
                            showLoading(false)
                        }
                        is State.Error -> {
                            showToast(state.message)
                            showLoading(false)
                        }
                    }
                }
        )

        mViewModel.movieReviewsLiveData.observe(this,
                { state ->
                    when (state) {
                        is State.Loading -> showLoading(true)
                        is State.Success -> {
                            setupMovieReviewsUi(state.data)
                            showLoading(false)
                        }
                        is State.Error -> {
                            showToast(state.message)
                            showLoading(false)
                        }
                    }
                }
        )

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getAllMovieDetailById(movieId!!)
        }

        mViewModel.similarMoviesLiveData.observe(this,
                { state ->
                    when (state) {
                        is State.Success -> {
                            setupSimilarMoviesUi(state.data)
                        }
                        is State.Error -> {
                            showToast(state.message)
                        }
                    }
                }
        )

        if (mViewModel.movieDetailLiveData.value !is State.Success) {
            getAllMovieDetailById(movieId!!)
        }
    }

    private fun setupSimilarMoviesUi(data: List<SimilarMovie>?) {
        if (data != null){
            mSimilarMoviesAdapter.submitList(data.take(12))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupMovieReviewsUi(data: MovieReviewsResponse?) {
        if (data != null){
            if (data.movieReviewsCount == 0){
                mViewBinding.movieReviewsRecyclerview.visibility = View.GONE
                mViewBinding.noReviewsTxt.visibility = View.VISIBLE
            }else{
                mViewBinding.reviewsCountTxt.text = "Reviews  (" + data.movieReviewsCount + ") "
                mViewBinding.noReviewsTxt.visibility = View.GONE
                mViewBinding.movieReviewsRecyclerview.visibility = View.VISIBLE
                mMovieReviewsAdapter.submitList(data.movieReviewsList)
            }
        }
    }

    private fun setupMovieCreditsUi(data: MovieCreditsResponse?) {
        if (data != null) {
            val movieCastList = data.movieCastList.take(5)
            val movieCrewList = data.movieCrewList.take(10)
            mMovieCastAdapter.submitList(movieCastList)
            mMovieCrewAdapter.submitList(movieCrewList)
        }
    }

    private fun getAllMovieDetailById(movieId: Int) {
        mViewModel.getShowDetailFromId(movieId)
        mViewModel.getMovieCreditsFromId(movieId)
        mViewModel.getMovieReviewsFromId(movieId)
        mViewModel.getSimilarMoviesFromId(movieId)
    }

    private fun initRecyclerViews() {
        val mCastLayoutManager = LinearLayoutManager(applicationContext)
        val mCrewLayoutManager = LinearLayoutManager(applicationContext)
        val mReviewsLayoutManager = LinearLayoutManager(applicationContext)
        val mSimilarMoviesLayoutManager = LinearLayoutManager(applicationContext)
        mCastLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mCrewLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mReviewsLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mSimilarMoviesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mViewBinding.movieCastRecyclerview.layoutManager = mCastLayoutManager
        mViewBinding.movieCrewRecyclerview.layoutManager = mCrewLayoutManager
        mViewBinding.movieReviewsRecyclerview.layoutManager = mReviewsLayoutManager
        mViewBinding.similarMoviesRecyclerview.layoutManager = mSimilarMoviesLayoutManager
        mViewBinding.movieCastRecyclerview.adapter = mMovieCastAdapter
        mViewBinding.movieCrewRecyclerview.adapter = mMovieCrewAdapter
        mViewBinding.movieReviewsRecyclerview.adapter = mMovieReviewsAdapter
        mViewBinding.similarMoviesRecyclerview.adapter = mSimilarMoviesAdapter
    }

    private fun initToolbar() {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mViewBinding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("SetTextI18n")
    private fun setupMovieSynopsisUi(movieDetail: MovieDetail?) {
        if (movieDetail !=null) {
            val movieBackdropImgUrl = MovieService.IMAGE_BASE_URL + movieDetail.movieBackdropImg
            val moviePosterImgUrl = MovieService.IMAGE_BASE_URL + movieDetail.moviePosterImg
            val movieRating: String = when{
                movieDetail.isAdultMovie -> "A"
                else -> "U/A"
            }
            val formattedDate = customDateFormat.format(originalDateFormat.parse(movieDetail.movieReleaseDate))
            val genreList = ArrayList<String>()
            movieDetail.movieGenreList.map {
                genreList.add(it.genreName)
            }

            val movieSubTitleText = convertRuntimeMillis(movieDetail.movieRuntime) + " \u25CF " +
                    movieRating + " \u25CF " + formattedDate


            mViewBinding.toolbarTitle.text = movieDetail.movieTitle
            mViewBinding.movieSubTitle.text = movieSubTitleText
            mViewBinding.movieGenre.text = genreList.joinToString(separator = ", ")
            mViewBinding.movieDescription.text = movieDetail.movieOverview

            mViewBinding.movieBackdrop.load(movieBackdropImgUrl) {
                error(R.drawable.ic_broken_image)
                crossfade(true)
                transformations(RoundedCornersTransformation(16.0f, 16.0f, 16.0f, 16.0f))
            }

            mViewBinding.moviePoster.load(moviePosterImgUrl){
                error(R.drawable.ic_broken_image)
                crossfade(true)
                transformations(RoundedCornersTransformation(8.0f, 8.0f, 8.0f, 8.0f))
            }

            mViewBinding.shareMovie.setOnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_SUBJECT, movieDetail.movieTitle)
                share.putExtra(Intent.EXTRA_TEXT, movieDetail.movieHomepageLink)
                startActivity(Intent.createChooser(share, "Share movie!"))
            }
        }
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(
                this,
                { isConnected ->
                    if (!isConnected) {
                        mViewBinding.textViewNetworkStatus.text =
                                getString(R.string.text_no_connectivity)
                        mViewBinding.networkStatusLayout.apply {
                            show()
                            setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                        }
                    } else {
                        if (mViewModel.movieDetailLiveData.value is State.Error) {
                            getAllMovieDetailById(movieId!!)
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

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun convertRuntimeMillis(runtimeMillis: Int): String {
        val h: Int = runtimeMillis / 60
        val m: Int = runtimeMillis % 60
        return   "$h h $m m"
    }
}
