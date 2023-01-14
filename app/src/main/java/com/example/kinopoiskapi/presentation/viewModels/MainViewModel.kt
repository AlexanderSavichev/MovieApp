package com.example.kinopoiskapi.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.data.repository.remotes.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies:MutableLiveData<MutableList<MovieDto>> = MutableLiveData()
    val movies: LiveData<MutableList<MovieDto>> = _movies

    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val compositeDisposable:CompositeDisposable = CompositeDisposable()
    private var page:Int = 1

    fun loadMovies() {
        if (_isLoading.value == true)
            return
        val disposable: Disposable =
            ApiFactory.apiService.loadMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _isLoading.value = true
                }
                .doAfterTerminate {
                    _isLoading.value = false
                }

            .subscribe(
                {value ->
                    val loadedMovies: MutableList<MovieDto>? = movies.value
                    if (loadedMovies != null){
                        loadedMovies.addAll(value.movies)
                        _movies.setValue(loadedMovies)}
                    else
                        _movies.setValue(value.movies)
                    page++

                    Log.d("PAGE", page.toString())
                },
                {value -> println("Received: $value") }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}