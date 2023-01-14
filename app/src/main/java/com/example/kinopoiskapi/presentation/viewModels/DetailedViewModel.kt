package com.example.kinopoiskapi.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kinopoiskapi.data.repository.models.TrailerDto
import com.example.kinopoiskapi.data.repository.models.TrailerResponse
import com.example.kinopoiskapi.data.repository.remotes.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailedViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _trailers: MutableLiveData<MutableList<TrailerDto>> = MutableLiveData()
    val trailers: LiveData<MutableList<TrailerDto>> = _trailers

    fun loadTrailers(id: Int) {
        val disposable: Disposable =
            ApiFactory.apiService.loadTrailers(id)
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