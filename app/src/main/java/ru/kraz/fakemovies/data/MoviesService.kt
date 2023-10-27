package ru.kraz.fakemovies.data

import retrofit2.http.GET

interface MoviesService {
    @GET("sequeniatesttask/films.json")
    suspend fun fetchMovies(): FakeMoviesCloud
}