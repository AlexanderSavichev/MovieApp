package com.example.kinopoiskapi.data.repository.remotes

import com.example.kinopoiskapi.data.repository.models.MovieResponse
import com.example.kinopoiskapi.data.repository.models.TrailerResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie?field=rating.kp&search=4-8&sortField=votes.kp&sortType=-1&limit=20")
    fun loadMovies(@Query("page") page:Int,
                   @Query("token") token:String?)
    :Single<MovieResponse>

    @GET("movie?field=id")
    fun loadTrailers(@Query("search") id:Int,
                     @Query("token") token:String?)
    :Single<TrailerResponse>
}

//token=647P77P-HSXMYYE-G788D1Q-NRWWNC9