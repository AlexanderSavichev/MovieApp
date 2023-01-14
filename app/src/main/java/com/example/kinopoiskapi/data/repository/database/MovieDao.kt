package com.example.kinopoiskapi.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kinopoiskapi.data.repository.models.MovieDto
import io.reactivex.rxjava3.core.Completable

@Dao
interface MovieDao {

    @Query("SELECT * FROM favourites")
    fun getAllFavouriteMovies():LiveData<List<MovieDto>>

    @Query("SELECT * FROM favourites WHERE id = :movieId")
    fun getFavouriteMovie(movieId:Int):LiveData<MovieDto>

    @Insert
    fun addMovie(movie:MovieDto):Completable

    @Query("DELETE FROM favourites WHERE id = :movieId")
    fun removeMovie(movieId:Int):Completable

}