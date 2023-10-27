package ru.kraz.fakemovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kraz.fakemovies.data.MoviesCloudDataSource
import ru.kraz.fakemovies.data.MoviesCloudDataSourceImpl
import ru.kraz.fakemovies.data.MoviesService

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {

    @Provides
    fun provideMoviesCloudDataSource(service: MoviesService): MoviesCloudDataSource =
        MoviesCloudDataSourceImpl(service)
}