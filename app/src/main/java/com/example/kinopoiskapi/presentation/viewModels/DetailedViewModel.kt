package com.example.kinopoiskapi.presentation.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kinopoiskapi.ActivityPreferences
import com.example.kinopoiskapi.data.repository.database.MovieDatabase
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.data.repository.models.TrailerDto
import com.example.kinopoiskapi.data.repository.remotes.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailedViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDatabase = MovieDatabase.getInstance(application)?.movieDao()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _trailers: MutableLiveData<MutableList<TrailerDto>> = MutableLiveData()
    val trailers: LiveData<MutableList<TrailerDto>> = _trailers

    fun isMovieFavourite (id:Int): LiveData<MovieDto>? {
        return movieDatabase?.getFavouriteMovie(id)
    }

    fun addMovie (movie:MovieDto){
        val disposable: Disposable? = movieDatabase?.addMovie(movie)
            ?.subscribeOn(Schedulers.io())
            ?.subscribe()
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }

    fun removeMovie (id:Int){
        val disposable: Disposable? = movieDatabase?.removeMovie(id)
            ?.subscribeOn(Schedulers.io())
            ?.subscribe()
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }

    fun loadTrailers(id: Int, context: Context) {
        val token: String? = ActivityPreferences().getToken(context)
        val disposable: Disposable =
            ApiFactory.apiService.loadTrailers(id, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { value ->
                        _trailers.setValue(value.trailers.trailerList as MutableList<TrailerDto>?)
                    },
                    { value -> println("Received: $value") }
                )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}