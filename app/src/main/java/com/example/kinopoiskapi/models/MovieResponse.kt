package com.example.kinopoiskapi.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(@SerializedName("docs") var movies: MutableList<Movie>)


