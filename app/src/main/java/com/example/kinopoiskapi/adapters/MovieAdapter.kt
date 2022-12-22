package com.example.kinopoiskapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.models.Movie
import com.example.kinopoiskapi.viewHolder.MovieViewHolder


class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var onReachEndListener: OnReachEndListener? = null
    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }


    private val movies: MutableList<Movie> = ArrayList()

    fun setData(newList: List<Movie>) {
        val diffCallback = MovieDiffCallback(movies, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movies.clear()
        movies.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.bind(movie)
        if (position == movies.size-1 && onReachEndListener!=null){
            onReachEndListener?.onReachEnd()
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}