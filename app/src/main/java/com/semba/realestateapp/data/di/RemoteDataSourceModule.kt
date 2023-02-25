package com.semba.realestateapp.data.di

import com.semba.realestateapp.data.remote.datasource.NetworkDataSource
import com.semba.realestateapp.data.remote.datasource.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindsRemoteDataSource(dataSource: NetworkDataSource): RemoteDataSource
}