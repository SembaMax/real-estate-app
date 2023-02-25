package com.semba.realestateapp.testing.di

import com.semba.realestateapp.data.Repository
import com.semba.realestateapp.data.di.RepositoryModule
import com.semba.realestateapp.testing.TestListingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestRepositoryModule {

    @Binds
    abstract fun bindTestRepository(repository: TestListingsRepository): Repository
}