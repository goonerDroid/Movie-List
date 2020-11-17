@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.sublime.movielist.ui.movieDetail

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sublime.movielist.R
import com.sublime.movielist.data.remote.MovieService
import com.sublime.movielist.databinding.ItemSimilarMovieBinding
import com.sublime.movielist.model.SimilarMovie
import java.text.SimpleDateFormat
import java.util.*

class SimilarMovieViewHolder (private val binding: ItemSimilarMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    private val originalDateFormat =  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val customDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())

    @SuppressLint("SetTextI18n")
    fun bind(similarMovie: SimilarMovie) {
        binding.similarMovieTitle.text = similarMovie.movieTitle
        val formattedDate = customDateFormat.format(originalDateFormat.parse(similarMovie.movieReleaseDate))
        binding.similarMovieRelease.text = formattedDate
        binding.movieBackdrop.load(MovieService.IMAGE_BASE_URL + similarMovie.movieBackdropPath) {
            error(R.drawable.ic_broken_image)
            crossfade(true)
            transformations(RoundedCornersTransformation(8.0f,8.0f,8.0f,8.0f))
        }

        binding.moviePoster.load(MovieService.IMAGE_BASE_URL + similarMovie.moviePosterPath) {
            error(R.drawable.ic_broken_image)
            crossfade(true)
            transformations(RoundedCornersTransformation(8.0f,8.0f,8.0f,8.0f))
        }

        when{
            similarMovie.movieVoteCount != 0 -> binding.similarLikesCount.text = ""+ similarMovie.movieVoteCount + " likes"
            similarMovie.movieVoteCount == 0  -> binding.similarLikesCount.visibility = View.INVISIBLE
            else -> binding.similarLikesCount.visibility = View.VISIBLE
        }
    }
}
