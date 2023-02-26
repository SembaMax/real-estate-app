package com.semba.realestateapp.testing.di

import com.semba.realestateapp.core.NetworkMonitor
import com.semba.realestateapp.core.di.ConnectivityModule
import com.semba.realestateapp.testing.FakeNetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ConnectivityModule::class]
)
abstract class TestConnectivityModule {

    @Binds
    abstract fun bindsConnectivityManager(networkMonitor: FakeNetworkMonitor): NetworkMonitor
}