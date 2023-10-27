package ru.kraz.fakemovies.data

import ru.kraz.fakemovies.domain.MovieDomain

data class MovieData(
    val id: Int,
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double,
    val imageUrl: String,
    val description: String,
    val genres: List<String>,
) {
    fun map(): MovieDomain = MovieDomain(
        id, localizedName, name, year, rating, imageUrl, description, genres
    )
}