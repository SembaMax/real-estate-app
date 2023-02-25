package com.semba.realestateapp.feature.home.domain

import com.semba.realestateapp.data.Repository
import com.semba.realestateapp.feature.home.ui.HomeScreenUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetListingsUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke() : Flow<HomeScreenUiState> =
        repository.loadListings()
            .map {
                if (it.errorCode != null)
                    HomeScreenUiState.Error(it.errorCode)
                else {
                    HomeScreenUiState.Success(it.data ?: emptyList())
                }
            }
            .onStart { emit(HomeScreenUiState.Loading) }
            .catch { emit(HomeScreenUiState.Error(-1)) }
}