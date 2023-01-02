package com.example.kinopoiskapi.data.repository.remotes

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {
    companion object {
        private const val BASE_URL = "https://api.kinopoisk.dev"
        private val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }

}