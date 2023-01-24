package com.example.kinopoiskapi.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.viewHolder.FavouritesViewHolder

class FavouritesAdapter(private val clickListener: MovieClickListener): RecyclerView.Adapter<FavouritesViewHolder>() {

    private val movies: MutableList<MovieDto> = ArrayList()
    fun setData(newList: List<MovieDto>) {
        val diffCallback = MovieDiffCallback(movies, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movies.clear()
        movies.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val movie: MovieDto = movies[position]
        holder.bind(movie, clickListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    interface MovieClickListener {
        fun onMovieClick(movie: MovieDto)
    }
}