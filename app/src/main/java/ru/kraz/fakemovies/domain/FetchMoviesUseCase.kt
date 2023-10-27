package ru.kraz.fakemovies.domain

import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): ResultFDS<List<MovieDomain>> {
        return repository.fetchMovies()
    }
}