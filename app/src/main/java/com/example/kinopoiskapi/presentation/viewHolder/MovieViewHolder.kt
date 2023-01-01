package com.example.kinopoiskapi.presentation.viewHolder

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.elements.RatingDefiner
import com.example.kinopoiskapi.presentation.views.FavouriteView
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object{
        const val DURATION = 100L
        const val PRESSED_ANIMATION = 1.2F
        const val UNPRESSED_ANIMATION = 1.0F
    }
    val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
    val textViewRatingKP: TextView = itemView.findViewById(R.id.textViewRatingKP)
    val textViewRatingIMDB: TextView = itemView.findViewById(R.id.textViewRatingIMDB)
    val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    val cvSmile: FavouriteView = itemView.findViewById(R.id.cvSmile)

    fun bind(movie: MovieDto) {

        if (movie.isFavourite) {
            itemView.cvSmile.happinessState = FavouriteView.Happiness.HAPPY.param
        } else if (movie.isBad) {
            itemView.cvSmile.happinessState = FavouriteView.Happiness.SAD.param
        } else
            itemView.cvSmile.happinessState = FavouriteView.Happiness.NEUTRAL.param

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

        itemView.animation=AnimationUtils.loadAnimation(itemView.context, R.anim.view_anim)

        itemView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.animate().scaleX(PRESSED_ANIMATION).scaleY(PRESSED_ANIMATION).setDuration(
                        DURATION
                    ).start()
                    (itemView.parent as RecyclerView).addOnItemTouchListener(object :
                        RecyclerView.OnItemTouchListener {
                        override fun onInterceptTouchEvent(rv: RecyclerView,
                                                           e: MotionEvent): Boolean {
                            if (rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                                view.animate().scaleX(UNPRESSED_ANIMATION).scaleY(
                                    UNPRESSED_ANIMATION
                                ).setDuration(DURATION).start()
                            }
                            return false               }
                        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                        override fun
                            onRequestDisallowInterceptTouchEvent(disallowIntercept:
                                                                 Boolean) { }})
                }
                MotionEvent.ACTION_UP -> {
                    view.animate().scaleX(UNPRESSED_ANIMATION).scaleY(UNPRESSED_ANIMATION).setDuration(
                        DURATION
                    ).start()
                    view.performClick()
                }
            }
            true
        }

        cvSmile.setOnClickListener {
            if ((it as FavouriteView).happinessState == FavouriteView.Happiness.NEUTRAL.param) {
                it.happinessState = FavouriteView.Happiness.HAPPY.param
                movie.isFavourite = true
                movie.isBad = false
            } else if (it.happinessState == FavouriteView.Happiness.HAPPY.param) {
                it.happinessState = FavouriteView.Happiness.SAD.param
                movie.isBad = true
                movie.isFavourite = false
            } else if (it.happinessState == FavouriteView.Happiness.SAD.param) {
                it.happinessState = FavouriteView.Happiness.NEUTRAL.param
                movie.isBad = false
                movie.isFavourite = false
            }

        }
    }
}