package com.sublime.movielist.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sublime.movielist.databinding.ItemMovieReviewBinding
import com.sublime.movielist.model.ReviewResult

class MovieReviewsListAdapter : ListAdapter<ReviewResult, MovieReviewViewHolder>(MovieReviewListDiff()) {

    private class MovieReviewListDiff : DiffUtil.ItemCallback<ReviewResult>() {

        override fun areItemsTheSame(oldItem: ReviewResult, newItem: ReviewResult): Boolean {
            return oldItem.movieReviewId == newItem.movieReviewId
        }

        override fun areContentsTheSame(oldItem: ReviewResult, newItem: ReviewResult): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  MovieReviewViewHolder (
        ItemMovieReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}