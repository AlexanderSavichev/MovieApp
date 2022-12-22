package com.example.kinopoiskapi.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kinopoiskapi.models.Movie
import com.example.kinopoiskapi.service.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val movies:MutableLiveData<MutableList<Movie>> = MutableLiveData()


    private val compositeDisposable:CompositeDisposable = CompositeDisposable()
    private var page:Int = 1

    fun loadMovies() {
        val disposable: Disposable =
            ApiFactory.apiService.loadMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {value ->
                    val loadedMovies: MutableList<Movie>? = movies.value
                    if (loadedMovies != null){
                        loadedMovies.addAll(value.movies)
                        movies.setValue(loadedMovies)}
                    else
                        movies.setValue(value.movies)
                    page++

                    Log.d("PAGE", page.toString())
                },
                {value -> println("Recieved: $value") }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}