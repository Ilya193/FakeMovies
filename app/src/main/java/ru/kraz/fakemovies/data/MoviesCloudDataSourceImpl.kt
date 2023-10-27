package ru.kraz.fakemovies.data

import javax.inject.Inject

class MoviesCloudDataSourceImpl @Inject constructor(
    private val service: MoviesService
) : MoviesCloudDataSource {
    override suspend fun fetchMovies(): FakeMoviesCloud {
        return service.fetchMovies()
    }
}