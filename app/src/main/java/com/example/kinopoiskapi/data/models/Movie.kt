package com.example.kinopoiskapi.data.models

import com.google.gson.annotations.SerializedName

data class Movie(@SerializedName("id") val id: Int,
                 @SerializedName("name") val name: String,
                 @SerializedName("description") val description: String,
                 @SerializedName("shortDescription") val shortDescription: String,
                 @SerializedName("year") val year: Int,
                 @SerializedName("poster") val poster: Poster,
                 @SerializedName("rating") val rating: Rating,
                 var isFavourite:Boolean,
                 var isBad:Boolean)



