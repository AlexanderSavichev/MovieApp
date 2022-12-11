package com.example.kinopoiskapi.models

import com.google.gson.annotations.SerializedName

data class Rating(@SerializedName("kp") var kp: Double, @SerializedName("imdb") var imdb: Double)