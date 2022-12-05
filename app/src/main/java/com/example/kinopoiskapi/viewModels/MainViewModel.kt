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

class MainViewModel: AndroidViewModel {
    constructor(application: Application) : super(application)

    val movies:MutableLiveData<List<Movie>> = MutableLiveData()


    private val compositeDisposable:CompositeDisposable = CompositeDisposable()
    private var page:Int = 1

    public fun loadMovies() {
        var disposable: Disposable =
            ApiFactory.apiService.loadMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {value ->
                    page++
                    movies.setValue(value.movies)
                    Log.d("TAG123", movies.toString())
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