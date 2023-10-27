package ru.kraz.fakemovies.data

interface MoviesCloudDataSource {
    suspend fun fetchMovies(): FakeMoviesCloud
}