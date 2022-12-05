package com.example.kinopoiskapi.models

import com.google.gson.annotations.SerializedName

class Rating {
    @SerializedName("kp")
    var kp:Double
    @SerializedName("imdb")
    var imdb:Double

    constructor(kp: Double, imdb: Double) {
        this.kp = kp
        this.imdb = imdb
    }

    override fun toString(): String {
        return "Rating(kp='$kp', imdb='$imdb')"
    }

}