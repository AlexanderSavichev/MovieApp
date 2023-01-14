package com.example.kinopoiskapi.data.repository.models

import com.google.gson.annotations.SerializedName

data class TrailerList(@SerializedName("trailers")val trailerList: List<TrailerDto>)
