package com.example.kinopoiskapi.models

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("id")
    var id: Int

    @SerializedName("name")
    var name: String

    @SerializedName("description")
    var description: String

    @SerializedName("shortDescription")
    var shortDescription: String

    @SerializedName("year")
    var year: Int

    @SerializedName("poster")
    var poster: Poster

    @SerializedName("rating")
    var rating: Rating

    constructor(
        id: Int,
        name: String,
        description: String,
        shortDescription:String,
        year: Int,
        poster: Poster,
        rating: Rating
    ) {
        this.id = id
        this.name = name
        this.description = description
        this.shortDescription = shortDescription
        this.year = year
        this.poster = poster
        this.rating = rating
    }

    override fun toString(): String {
        return "Movie(id=$id, name='$name', description='$description',shortDescription='$shortDescription' year=$year, poster=$poster, rating=$rating)"
    }

}