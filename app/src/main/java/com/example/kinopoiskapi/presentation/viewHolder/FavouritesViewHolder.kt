package com.example.kinopoiskapi.presentation.viewHolder

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.elements.RatingDefiner

class FavouritesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    private val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
    private val textViewRatingKP: TextView = itemView.findViewById(R.id.textViewRatingKP)
    private val textViewRatingIMDB: TextView = itemView.findViewById(R.id.textViewRatingIMDB)
    private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)

    fun bind (movie:MovieDto){
        Glide.with(itemView)
            .load(movie.poster.url)
            .into(imageViewPoster)
        val backgroundKp: Drawable? = ResourcesCompat.getDrawable(
            itemView.context.resources,
            RatingDefiner().colorOfRatingKp(movie),
            itemView.context.theme
        )
        val backgroundImdb: Drawable? =
            ResourcesCompat.getDrawable(
                itemView.context.resources,
                RatingDefiner().colorOfRatingImdb(movie),
                itemView.context.theme
            )

        textViewRatingKP.background = backgroundKp
        textViewRatingKP.text = movie.rating.kp.toString().subSequence(0, 3)
        textViewRatingIMDB.background = backgroundImdb
        textViewRatingIMDB.text = movie.rating.imdb.toString()
        textViewDescription.text = movie.shortDescription
    }
}