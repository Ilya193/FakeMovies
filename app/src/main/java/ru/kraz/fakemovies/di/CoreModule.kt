package ru.kraz.fakemovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.kraz.fakemovies.presentation.ResourceProvider
import ru.kraz.fakemovies.presentation.ResourceProviderImpl

@Module
@InstallIn(ViewModelComponent::class)
class CoreModule {

    @Provides
    fun provideResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider = ResourceProviderImpl(context)
}