package com.sublime.movielist.ui.movieDetail

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.sublime.movielist.R
import com.sublime.movielist.data.remote.MovieService
import com.sublime.movielist.databinding.ItemMovieCrewBinding
import com.sublime.movielist.model.MovieCast

class MovieCrewViewHolder (private val binding: ItemMovieCrewBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(movieCast: MovieCast) {
        binding.movieCrewPoster.load(MovieService.IMAGE_BASE_URL + movieCast.castProfilePath) {
            error(R.drawable.ic_broken_image)
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.movieCrewName.text = movieCast.castName
        binding.movieCrewRole.text = "as "+movieCast.castCharacter

        if (movieCast.crewJob == null) binding.movieCrewRole.text = "as "+movieCast.castCharacter
        else binding.movieCrewRole.text = movieCast.crewJob
    }
}
