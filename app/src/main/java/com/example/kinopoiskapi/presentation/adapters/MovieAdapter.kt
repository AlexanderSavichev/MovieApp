package com.example.kinopoiskapi.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.viewHolder.MovieViewHolder
import com.example.kinopoiskapi.presentation.views.FavouriteView


class MovieAdapter(private val clickListener: SmileClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    private var onReachEndListener: OnReachEndListener? = null
    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }


    private val movies: MutableList<MovieDto> = ArrayList()

    fun setData(newList: List<MovieDto>) {
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
        val movie: MovieDto = movies[position]
        holder.bind(movie, clickListener, position)
        if (position == movies.size-1 && onReachEndListener!=null){
            onReachEndListener?.onReachEnd()
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    interface SmileClickListener{
        fun onSmileClick (movie:MovieDto, position: Int, happiness:FavouriteView.Happiness)
    }

}