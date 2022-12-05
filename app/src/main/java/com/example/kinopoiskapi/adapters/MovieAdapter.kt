package com.example.kinopoiskapi.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.models.Movie
import com.google.gson.JsonObject
import org.json.JSONObject

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    var movies: List<Movie> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class MovieViewHolder : RecyclerView.ViewHolder {
        var imageViewPoster: ImageView
        var textViewRatingKP: TextView
        var textViewRatingIMDB: TextView
        var textViewDescription: TextView


        constructor(itemView: View) : super(itemView) {
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster)
            textViewRatingKP = itemView.findViewById(R.id.textViewRatingKP)
            textViewRatingIMDB = itemView.findViewById(R.id.textViewRatingIMDB)
            textViewDescription = itemView.findViewById(R.id.textViewDescription)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies[position]
        Glide.with(holder.itemView)
            .load(movie.poster.url)
            .into(holder.imageViewPoster)
        val backgroundKp: Drawable? =
            ContextCompat.getDrawable(holder.itemView.context, setColorOfRatingKp(movie))
        val backgroundImdb: Drawable? =
            ContextCompat.getDrawable(holder.itemView.context, setColorOfRatingImdb(movie))
        holder.textViewRatingKP.setBackground(backgroundKp)
        holder.textViewRatingKP.setText(movie.rating.kp.toString().subSequence(0, 3))
        holder.textViewRatingIMDB.setBackground(backgroundImdb)
        holder.textViewRatingIMDB.setText(movie.rating.imdb.toString())
        holder.textViewDescription.setText(movie.shortDescription)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setColorOfRatingKp(movie: Movie): Int {
        var ratingKP: Double = movie.rating.kp
        var backgroundIdKp: Int

        if (ratingKP >= 7)
            backgroundIdKp = R.drawable.rating_green
        else if (ratingKP >= 5)
            backgroundIdKp = R.drawable.rating_yellow
        else
            backgroundIdKp = R.drawable.rating_red

        return backgroundIdKp
    }

    fun setColorOfRatingImdb(movie: Movie): Int {
        var ratingIMDB: Double = movie.rating.imdb
        var backgroundIdImdb: Int

        if (ratingIMDB >= 7)
            backgroundIdImdb = R.drawable.rating_green
        else if (ratingIMDB >= 5)
            backgroundIdImdb = R.drawable.rating_yellow
        else
            backgroundIdImdb = R.drawable.rating_red

        return backgroundIdImdb
    }
}