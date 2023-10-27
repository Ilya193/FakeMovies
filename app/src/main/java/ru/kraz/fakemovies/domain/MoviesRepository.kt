package ru.kraz.fakemovies.domain

interface MoviesRepository {
    suspend fun fetchMovies(): ResultFDS<List<MovieDomain>>
    suspend fun <T> handleExceptions(block: suspend () -> ResultFDS<T>): ResultFDS<T>
}