package com.sublime.movielist.ui.movieDetail

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.sublime.movielist.R
import com.sublime.movielist.data.remote.MovieService
import com.sublime.movielist.databinding.ItemMovieCrewBinding
import com.sublime.movielist.databinding.ItemMovieReviewBinding
import com.sublime.movielist.model.MovieCast
import com.sublime.movielist.model.ReviewResult

class MovieReviewViewHolder (private val binding: ItemMovieReviewBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(movieReviewResult: ReviewResult) {
        binding.reviewAuthor.text = movieReviewResult.movieReviewAuthor
        binding.reviewContent.text = movieReviewResult.movieReviewContent
    }
}
