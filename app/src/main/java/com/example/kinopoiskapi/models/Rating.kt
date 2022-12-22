package com.example.kinopoiskapi.models

import com.google.gson.annotations.SerializedName

data class Rating(@SerializedName("kp") val kp: Double, @SerializedName("imdb") val imdb: Double)