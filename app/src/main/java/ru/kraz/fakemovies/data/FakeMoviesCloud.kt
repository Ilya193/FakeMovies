package ru.kraz.fakemovies.data

import com.google.gson.annotations.SerializedName

data class FakeMoviesCloud(
    @SerializedName("films")
    val films: List<MovieCloud>
)