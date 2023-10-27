package ru.kraz.fakemovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kraz.fakemovies.domain.MovieDomain
import ru.kraz.fakemovies.domain.ToUiMapper
import ru.kraz.fakemovies.presentation.MovieUi
import ru.kraz.fakemovies.presentation.ToMovieUiMapper

@Module
@InstallIn(ViewModelComponent::class)
class MappersModule {

    @Provides
    fun provideToMovieUiMapper(): ToUiMapper<MovieDomain, MovieUi> =
        ToMovieUiMapper()
}