package com.sublime.movielist.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sublime.movielist.databinding.ItemSimilarMovieBinding
import com.sublime.movielist.model.SimilarMovie

class SimilarMoviesListAdapter  : ListAdapter<SimilarMovie, SimilarMovieViewHolder>(
    SimilarMoviesListDiff()
) {

    private class SimilarMoviesListDiff : DiffUtil.ItemCallback<SimilarMovie>() {

        override fun areItemsTheSame(oldItem: SimilarMovie, newItem: SimilarMovie): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: SimilarMovie, newItem: SimilarMovie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimilarMovieViewHolder (
        ItemSimilarMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}