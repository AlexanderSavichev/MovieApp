package com.example.kinopoiskapi.data.repository.models

import com.google.gson.annotations.SerializedName

data class TrailerDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,

)