package ru.kraz.fakemovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kraz.fakemovies.data.MoviesCloudDataSource
import ru.kraz.fakemovies.data.MoviesRepositoryImpl
import ru.kraz.fakemovies.domain.MoviesRepository

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideMoviesRepository(cloudDataSource: MoviesCloudDataSource): MoviesRepository =
        MoviesRepositoryImpl(cloudDataSource)
}