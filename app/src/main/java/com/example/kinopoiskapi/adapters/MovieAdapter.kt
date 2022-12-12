package com.example.kinopoiskapi.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.models.Movie
import com.example.kinopoiskapi.views.FavouriteView
import com.example.kinopoiskapi.views.FavouriteView.Companion.HAPPY
import com.example.kinopoiskapi.views.FavouriteView.Companion.NEUTRAL
import com.example.kinopoiskapi.views.FavouriteView.Companion.SAD

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies: MutableList<Movie> = ArrayList()

    fun setData(newList: List<Movie>) {
        val diffCallback = MovieDiffCallback(movies, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movies.clear()
        movies.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        var textViewRatingKP: TextView = itemView.findViewById(R.id.textViewRatingKP)
        var textViewRatingIMDB: TextView = itemView.findViewById(R.id.textViewRatingIMDB)
        var textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        var cvSmile:FavouriteView = itemView.findViewById(R.id.cvSmile)
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
        holder.cvSmile.setOnClickListener {
            if ((it as FavouriteView).happinessState == NEUTRAL)
                it.happinessState = HAPPY
            else if(it.happinessState == HAPPY)
                it.happinessState = SAD
            else if(it.happinessState == SAD)
                it.happinessState = NEUTRAL
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    fun setColorOfRatingKp(movie: Movie): Int {
        val ratingKP: Double = movie.rating.kp
        val backgroundIdKp: Int

        if (ratingKP >= 7)
            backgroundIdKp = R.drawable.rating_green
        else if (ratingKP >= 5)
            backgroundIdKp = R.drawable.rating_yellow
        else
            backgroundIdKp = R.drawable.rating_red

        return backgroundIdKp
    }

    fun setColorOfRatingImdb(movie: Movie): Int {
        val ratingIMDB: Double = movie.rating.imdb
        val backgroundIdImdb: Int

        if (ratingIMDB >= 7)
            backgroundIdImdb = R.drawable.rating_green
        else if (ratingIMDB >= 5)
            backgroundIdImdb = R.drawable.rating_yellow
        else
            backgroundIdImdb = R.drawable.rating_red

        return backgroundIdImdb
    }

}