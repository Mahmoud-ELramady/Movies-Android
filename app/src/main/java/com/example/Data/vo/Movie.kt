package com.example.Data.vo


import com.google.gson.annotations.SerializedName

data class Movie(

    val genreIds: List<Int>,
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String

)