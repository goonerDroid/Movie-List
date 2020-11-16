package com.sublime.movielist.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sublime.movielist.databinding.ItemNowPlayingBinding
import com.sublime.movielist.model.NowPlayingMovie

class MovieListAdapter(
    private val onItemClicked: (NowPlayingMovie, ImageView) -> Unit
) : ListAdapter<NowPlayingMovie,NowPlayingMovieViewHolder>(NowPlayingMovieListDiff()) {

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

    private class NowPlayingMovieListDiff : DiffUtil.ItemCallback<NowPlayingMovie>() {

        override fun areItemsTheSame(oldItem: NowPlayingMovie, newItem: NowPlayingMovie): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: NowPlayingMovie, newItem: NowPlayingMovie): Boolean {
            return oldItem == newItem
        }
    }
}
