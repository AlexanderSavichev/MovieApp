package com.example.kinopoiskapi.data.repository.models

import com.google.gson.annotations.SerializedName

data class TrailerResponse(@SerializedName("videos")val trailers: TrailerList)
