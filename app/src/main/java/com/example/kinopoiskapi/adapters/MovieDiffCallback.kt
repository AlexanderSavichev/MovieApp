package com.example.kinopoiskapi.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskapi.models.Movie

class MovieDiffCallback(private val oldList: List<Movie>, private val newList: List<Movie>):DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name === newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie: Movie = oldList[oldItemPosition]
        val newMovie: Movie = newList[newItemPosition]
        return (oldMovie.name.equals(newMovie.name)
                && oldMovie.shortDescription == newMovie.shortDescription
                && oldMovie.year == newMovie.year)
    }

}