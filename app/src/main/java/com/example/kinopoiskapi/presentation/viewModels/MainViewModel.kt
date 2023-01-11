package com.example.kinopoiskapi.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.data.repository.remotes.ApiFactory

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies:MutableLiveData<MutableList<MovieDto>> = MutableLiveData()
    val movies: LiveData<MutableList<MovieDto>> = _movies
    private var page:Int = 1

    suspend fun loadMovies() {

                val data = ApiFactory.apiService.loadMovies(page)
                val loadedMovies: MutableList<MovieDto>? = movies.value
                if (loadedMovies != null){
                    loadedMovies.addAll(data.movies)
                    _movies.setValue(loadedMovies)}
                else
                    _movies.setValue(data.movies)
                page++
    }
}