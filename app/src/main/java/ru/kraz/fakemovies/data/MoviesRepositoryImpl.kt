package ru.kraz.fakemovies.data

import retrofit2.HttpException
import ru.kraz.fakemovies.domain.ErrorType
import ru.kraz.fakemovies.domain.MovieDomain
import ru.kraz.fakemovies.domain.MoviesRepository
import ru.kraz.fakemovies.domain.ResultFDS
import java.net.UnknownHostException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val cloudDataSource: MoviesCloudDataSource,
) : MoviesRepository {
    override suspend fun fetchMovies(): ResultFDS<List<MovieDomain>> {
        return handleExceptions {
            val movies = cloudDataSource.fetchMovies().films.map { it.map() }
            ResultFDS.Success(movies.map { it.map() })
        }
    }

    override suspend fun <T> handleExceptions(block: suspend () -> ResultFDS<T>): ResultFDS<T> {
        return try {
            block()
        } catch (e: UnknownHostException) {
            ResultFDS.Error(ErrorType.NO_CONNECTION)
        } catch (e: HttpException) {
            ResultFDS.Error(ErrorType.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
            ResultFDS.Error(ErrorType.GENERIC_ERROR)
        }
    }
}