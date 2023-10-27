package ru.kraz.fakemovies.presentation

import android.graphics.Movie
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

    private val _movie = MutableLiveData<EventWrapper<MovieUi.Success>>()
    val movie: LiveData<EventWrapper<MovieUi.Success>> get() = _movie

    private val _uiState = MutableLiveData<MovieUiState>()
    val uiState: LiveData<MovieUiState> get() = _uiState

    fun fetchMovies() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.postValue(MovieUiState.Loading)
        when (val result = fetchMoviesUseCase()) {
            is ResultFDS.Success -> {
                val movies = result.data.map {
                    mapper.map(it)
                }
                _uiState.postValue(MovieUiState.Success(movies))
            }

            is ResultFDS.Error -> _uiState.postValue(
                MovieUiState.Error(resourceProvider.getString(result.e))
            )
        }
    }

    fun setMovie(movie: MovieUi.Success) {
        _movie.value = EventWrapper.Single(movie)
    }
}