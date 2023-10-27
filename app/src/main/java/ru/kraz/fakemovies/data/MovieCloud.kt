package ru.kraz.fakemovies.data

import com.google.gson.annotations.SerializedName

data class MovieCloud(
    val description: String?,
    val genres: List<String>,
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("localized_name")
    val localizedName: String,
    val name: String,
    val rating: Double?,
    val year: Int,
) {
    fun map(): MovieData =
        MovieData(
            id, localizedName, name, year, rating ?: 0.0, imageUrl ?: "", description ?: "", genres
        )
}