package com.semba.realestateapp.testing

import com.semba.realestateapp.core.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class FakeNetworkMonitor: NetworkMonitor {

    override val isOnline: Flow<Boolean> = flow {
        emit(true)
    }
}