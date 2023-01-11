package com.example.kinopoiskapi.data.repository.remotes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {
    companion object {
        private const val BASE_URL = "https://api.kinopoisk.dev"
        private val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }
}