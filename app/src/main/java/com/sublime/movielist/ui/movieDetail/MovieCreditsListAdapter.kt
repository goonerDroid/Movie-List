package com.sublime.movielist.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sublime.movielist.databinding.ItemMovieCrewBinding
import com.sublime.movielist.model.MovieCast

class MovieCreditsListAdapter : ListAdapter<MovieCast, MovieCrewViewHolder>(MovieCrewListDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCrewViewHolder (
        ItemMovieCrewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MovieCrewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class MovieCrewListDiff : DiffUtil.ItemCallback<MovieCast>() {

        override fun areItemsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
            return oldItem.castId == newItem.castId
        }

        override fun areContentsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
            return oldItem == newItem
        }
    }

}