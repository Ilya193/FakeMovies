package ru.kraz.fakemovies.presentation

import com.example.studying.presentation.Comparing
import java.io.Serializable

sealed interface MovieUi : Comparing<MovieUi> {

    override fun same(item: MovieUi): Boolean = false
    override fun sameContent(item: MovieUi): Boolean = false

    data class Success(
        val id: Int,
        val localizedName: String,
        val name: String,
        val year: Int,
        val rating: Double,
        val imageUrl: String,
        val description: String,
        val genres: List<String>,
    ) : MovieUi, Serializable {
        override fun same(item: MovieUi): Boolean = item is Success && id == item.id
        override fun sameContent(item: MovieUi): Boolean = this == item
    }
}

sealed interface MovieUiState {
    data class Success(val list: List<MovieUi>): MovieUiState
    data class SuccessFilter(val list: List<MovieUi>): MovieUiState
    data object Empty : MovieUiState
    data class Error(val msg: String) : MovieUiState
    data object Loading : MovieUiState

    fun MovieUiState.isSuccess(): Boolean =
        this is Success || this is SuccessFilter

    fun MovieUiState.isLoading(): Boolean =
        this is Loading

    fun MovieUiState.isError(): Boolean =
        this is Error
}

