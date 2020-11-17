package com.sublime.movielist.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sublime.movielist.databinding.ItemNowPlayingBinding
import com.sublime.movielist.model.Movie

class MovieListAdapter(
    private val onItemClicked: (Movie, ImageView) -> Unit
) : ListAdapter<Movie,NowPlayingMovieViewHolder>(NowPlayingMovieListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= NowPlayingMovieViewHolder (
        ItemNowPlayingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
    )

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    private class NowPlayingMovieListDiff : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
