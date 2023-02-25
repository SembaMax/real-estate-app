package com.semba.realestateapp.feature.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.feature.detail.domain.GetListingDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getListingDetailUseCase: GetListingDetailUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)

    val uiState =_uiState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        DetailUiState.Loading
    )

    fun loadListingDetail(listingId: Int) {
        getListingDetailUseCase(listingId)
            .onEach {
                _uiState.value = it
            }.launchIn(viewModelScope)
    }
}

sealed interface DetailUiState {
    object Loading: DetailUiState
    data class Success(val listing: ListingModel): DetailUiState
    data class Error(val errorCode: Int?): DetailUiState
}