@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.sublime.movielist.ui.movieList

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sublime.movielist.R
import com.sublime.movielist.data.remote.MovieService
import com.sublime.movielist.databinding.ItemNowPlayingBinding
import com.sublime.movielist.model.NowPlayingMovie
import java.text.SimpleDateFormat
import java.util.*

class NowPlayingMovieViewHolder (private val binding: ItemNowPlayingBinding) : RecyclerView.ViewHolder(binding.root) {
    private val originalDateFormat =  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val customDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())


    @SuppressLint("SetTextI18n")
    fun bind(nowPlayingMovie: NowPlayingMovie, onItemClicked: (NowPlayingMovie, ImageView) -> Unit) {
        binding.movieTitle.text = nowPlayingMovie.movieTitle
        val formattedDate = customDateFormat.format(originalDateFormat.parse(nowPlayingMovie.movieReleaseDate))
        binding.movieRelease.text = formattedDate
        binding.moviePoster.load(MovieService.IMAGE_BASE_URL + nowPlayingMovie.moviePosterPath) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
            crossfade(true)
            transformations(RoundedCornersTransformation(16.0f,16.0f,16.0f,16.0f))
        }

        when{
            nowPlayingMovie.movieVoteCount != 0 -> binding.likesCount.text = ""+ nowPlayingMovie.movieVoteCount + " likes"
            nowPlayingMovie.movieVoteCount == 0  -> binding.likesCount.visibility = View.INVISIBLE
            else -> binding.likesCount.visibility = View.VISIBLE
        }

        binding.root.setOnClickListener {
            onItemClicked(nowPlayingMovie, binding.moviePoster)
        }
    }
}