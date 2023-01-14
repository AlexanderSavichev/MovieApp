package com.example.kinopoiskapi.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kinopoiskapi.data.repository.database.MovieDatabase
import com.example.kinopoiskapi.data.repository.models.MovieDto

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDatabase = MovieDatabase.getInstance(application)?.movieDao()

    fun getAllFavourites(): LiveData<List<MovieDto>>? {
        return movieDatabase?.getAllFavouriteMovies()
    }

}