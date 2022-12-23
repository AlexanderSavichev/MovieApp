package com.example.kinopoiskapi.domain.service

import com.example.kinopoiskapi.R
import com.example.kinopoiskapi.data.models.Movie


class RatingDefiner {
    companion object{
         const val GOOD_RATING = 7
         const val BAD_RATING = 5
    }
     fun colorOfRatingKp(movie: Movie): Int {
        val ratingKP: Double = movie.rating.kp
        return when {
            ratingKP >= GOOD_RATING ->  R.drawable.rating_green
            ratingKP >= BAD_RATING ->  R.drawable.rating_yellow
            else -> R.drawable.rating_red
        }
    }

     fun colorOfRatingImdb(movie: Movie): Int {
        val ratingIMDB: Double = movie.rating.imdb
        return when {
            ratingIMDB >= GOOD_RATING ->  R.drawable.rating_green
            ratingIMDB >= BAD_RATING ->  R.drawable.rating_yellow
            else -> R.drawable.rating_red
        }
    }
}