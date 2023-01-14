package com.example.kinopoiskapi.data.repository.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourites")
data class MovieDto(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("shortDescription") val shortDescription: String,
    @SerializedName("year") val year: Int,
    @Embedded @SerializedName("poster") val poster: PosterDto,
    @Embedded @SerializedName("rating") val rating: RatingDto,
    var isFavourite: Boolean,
) : java.io.Serializable



