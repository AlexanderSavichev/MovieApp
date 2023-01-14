package com.example.kinopoiskapi.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskapi.data.repository.models.TrailerDto

class TrailerDiffCallback(private val oldList: List<TrailerDto>, private val newList: List<TrailerDto>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name === newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTrailer: TrailerDto = oldList[oldItemPosition]
        val newTrailer: TrailerDto = newList[newItemPosition]
        return (oldTrailer.name == newTrailer.name
                && oldTrailer.url == newTrailer.url
               )
    }

}