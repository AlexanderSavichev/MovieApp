package com.example.kinopoiskapi.data.repository.models

import com.google.gson.annotations.SerializedName

data class RatingDto(@SerializedName("kp") val kp: Double, @SerializedName("imdb") val imdb: Double)