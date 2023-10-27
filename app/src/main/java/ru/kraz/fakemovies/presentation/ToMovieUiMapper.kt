package ru.kraz.fakemovies.presentation

import ru.kraz.fakemovies.domain.MovieDomain
import ru.kraz.fakemovies.domain.ToUiMapper

class ToMovieUiMapper : ToUiMapper<MovieDomain, MovieUi> {
    override fun map(data: MovieDomain): MovieUi =
        MovieUi.Success(
            data.id,
            data.localizedName,
            data.name,
            data.year,
            data.rating,
            data.imageUrl,
            data.description,
            data.genres
        )
}