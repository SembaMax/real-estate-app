package com.semba.realestateapp.feature.detail.domain

import com.semba.realestateapp.data.Repository
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.feature.detail.ui.DetailUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetListingDetailUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(listingId: Int): Flow<DetailUiState> =
        repository.loadListingDetail(listingId)
            .map {
                if (it.errorCode != null)
                    DetailUiState.Error(it.errorCode)
                else {
                    DetailUiState.Success(it.data ?: ListingModel.empty())
                }
            }
            .catch { emit(DetailUiState.Error(-1)) }
            .onStart { emit(DetailUiState.Loading) }
}