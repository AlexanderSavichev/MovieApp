package com.example.kinopoiskapi.data.repository.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(@SerializedName("docs") val movies: MutableList<MovieDto>)


