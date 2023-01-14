package com.example.kinopoiskapi.presentation.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.data.repository.models.TrailerDto
import com.example.kinopoiskapi.presentation.adapters.TrailerAdapter

class TrailerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private val trailerName:TextView = itemView.findViewById(R.id.trailerName)
    private val playImage:ImageView = itemView.findViewById(R.id.playImage)

    fun bind(trailer: TrailerDto,clickListener:TrailerAdapter.TrailerClickListener, position:Int){
        trailerName.text = trailer.name
        playImage.setOnClickListener{
            clickListener.onPlayClick(trailer, position)
        }
    }
}