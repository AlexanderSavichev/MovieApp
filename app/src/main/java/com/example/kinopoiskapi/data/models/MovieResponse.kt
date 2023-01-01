package com.example.kinopoiskapi.data.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(@SerializedName("docs") val movies: MutableList<MovieDto>)


