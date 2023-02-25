package com.semba.realestateapp.feature.home.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.feature.home.domain.GetListingsUseCase
import com.semba.realestateapp.design.navigation.LISTING_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getListingsUseCase: GetListingsUseCase): ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUiState> = MutableStateFlow(HomeScreenUiState.Loading)

    val uiState =_uiState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        HomeScreenUiState.Loading
    )

    init {
        loadData()
    }

    fun loadData() {
        fetchListings()
    }

    private fun fetchListings() {
        getListingsUseCase()
            .onEach { result -> _uiState.value = result }
            .launchIn(viewModelScope)
    }

}

fun ListingModel.toArgs(): Map<String,String> = mapOf(
    LISTING_ID_ARG to this.id.toString()
)

sealed interface HomeScreenUiState {
    object Loading: HomeScreenUiState
    data class Success(val listings: List<ListingModel> = emptyList()): HomeScreenUiState
    class Error(val errorCode: Int?): HomeScreenUiState
}

@Stable
interface TopBarState {
    val offset: Float
    val height: Float
    val progress: Float
    val consumed: Float
    var scrollTopLimitReached: Boolean
    var scrollOffset: Float
}