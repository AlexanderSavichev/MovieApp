package com.example.kinopoiskapi.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskapi.data.models.MovieDto

class MovieDiffCallback(private val oldList: List<MovieDto>, private val newList: List<MovieDto>):DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name === newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie: MovieDto = oldList[oldItemPosition]
        val newMovie: MovieDto = newList[newItemPosition]
        return (oldMovie.name == newMovie.name
                && oldMovie.shortDescription == newMovie.shortDescription
                && oldMovie.year == newMovie.year)
    }

}