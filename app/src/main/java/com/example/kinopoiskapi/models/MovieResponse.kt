package com.example.kinopoiskapi.models

import com.google.gson.annotations.SerializedName

class MovieResponse {

    @SerializedName("docs")
    var movies:List<Movie>

    constructor(movies: List<Movie>) {
        this.movies = movies
    }

    override fun toString(): String {
        return "MovieResponse(movies=$movies)"
    }

}