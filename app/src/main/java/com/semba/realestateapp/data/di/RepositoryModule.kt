package com.semba.realestateapp.data.di

import com.semba.realestateapp.data.ListingsRepository
import com.semba.realestateapp.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsImagesRepository(repository: ListingsRepository): Repository
}