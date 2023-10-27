package ru.kraz.fakemovies.presentation

import ru.kraz.fakemovies.domain.ErrorType

interface ResourceProvider {
    fun getString(errorType: ErrorType): String
}