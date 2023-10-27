package ru.kraz.fakemovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kraz.fakemovies.domain.FetchMoviesUseCase
import ru.kraz.fakemovies.domain.MoviesRepository

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideFetchMoviesUseCase(moviesRepository: MoviesRepository): FetchMoviesUseCase =
        FetchMoviesUseCase(moviesRepository)
}