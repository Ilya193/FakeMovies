package ru.kraz.fakemovies.domain

interface ToUiMapper<T, R> {
    fun map(data: T): R
}