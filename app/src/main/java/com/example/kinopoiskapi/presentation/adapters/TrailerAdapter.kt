package com.example.kinopoiskapi.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.data.repository.models.TrailerDto
import com.example.kinopoiskapi.presentation.viewHolder.TrailerViewHolder

class TrailerAdapter(private val clickListener: TrailerClickListener): RecyclerView.Adapter<TrailerViewHolder>() {

    private val trailers: MutableList<TrailerDto> = ArrayList()

    fun setData(newList: List<TrailerDto>) {
        val diffCallback = TrailerDiffCallback(trailers, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        trailers.clear()
        trailers.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        return TrailerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer: TrailerDto = trailers[position]
        holder.bind(trailer,clickListener, position)
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    interface TrailerClickListener{
        fun onPlayClick(trailer:TrailerDto, position: Int)
    }
}