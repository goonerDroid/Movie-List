package com.sublime.movielist.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sublime.movielist.databinding.ItemNowPlayingBinding
import com.sublime.movielist.model.NowPlayingMovie

class MovieListAdapter(
    private val onItemClicked: (NowPlayingMovie, ImageView) -> Unit
) : RecyclerView.Adapter<NowPlayingMovieViewHolder>() {

    private var mNowPlayingMovieList = ArrayList<NowPlayingMovie>()

    fun submitList(toMutableList: MutableList<NowPlayingMovie>){
        mNowPlayingMovieList.clear()
        mNowPlayingMovieList.addAll(toMutableList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NowPlayingMovieViewHolder (
        ItemNowPlayingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int {
        return mNowPlayingMovieList.size
    }

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        holder.bind(mNowPlayingMovieList[holder.adapterPosition], onItemClicked)
    }
}