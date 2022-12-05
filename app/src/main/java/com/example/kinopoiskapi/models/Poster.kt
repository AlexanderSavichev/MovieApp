package com.example.kinopoiskapi.models

import com.google.gson.annotations.SerializedName

class Poster {
    @SerializedName("url")
    var url:String
    constructor(url: String) {
        this.url = url
    }

    override fun toString(): String {
        return "Poster(url='$url')"
    }

}