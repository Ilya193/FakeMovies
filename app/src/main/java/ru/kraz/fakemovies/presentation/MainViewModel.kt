package ru.kraz.fakemovies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.fakemovies.domain.FetchMoviesUseCase
import ru.kraz.fakemovies.domain.MovieDomain
import ru.kraz.fakemovies.domain.ResultFDS
import ru.kraz.fakemovies.domain.ToUiMapper
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val mapper: ToUiMapper<MovieDomain, MovieUi>,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private var movies = mutableListOf<MovieUi.Success>()

    private val _movie = MutableLiveData<EventWrapper<MovieUi.Success>>()
    val movie: LiveData<EventWrapper<MovieUi.Success>> get() = _movie

    private val _uiState = MutableLiveData<MovieUiState>()
    val uiState: LiveData<MovieUiState> get() = _uiState

    fun fetchMovies() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.postValue(MovieUiState.Loading)
        when (val result = fetchMoviesUseCase()) {
            is ResultFDS.Success -> {
                val temp = result.data.map {
                    mapper.map(it)
                }
                movies.clear()
                temp.forEach { movies.add(it as MovieUi.Success) }
                _uiState.postValue(MovieUiState.Success(temp))
            }

            is ResultFDS.Error -> _uiState.postValue(
                MovieUiState.Error(resourceProvider.getString(result.e))
            )
        }
    }

    fun setMovie(movie: MovieUi.Success) {
        _movie.value = EventWrapper.Single(movie)
    }

    fun moviesFilter(genres: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        if (genres.isEmpty())
            _uiState.postValue(MovieUiState.Success(movies.map { it.copy() }))
        else {
            val temp = mutableListOf<MovieUi.Success>()
            genres.forEach { genre ->
                movies.forEach { movie ->
                    if (movie.genres.any { it == genre }) temp.add(movie)
                }
            }
            _uiState.postValue(MovieUiState.Success(temp))
        }
    }
}