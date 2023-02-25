package com.semba.realestateapp.feature.detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.design.component.ErrorView
import com.semba.realestateapp.design.component.LoadingView

@Composable
fun DetailScreen(listingId: Int) {
    val viewModel: DetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.loadListingDetail(listingId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is DetailUiState.Error -> ErrorView(modifier = Modifier.align(Alignment.Center))
            DetailUiState.Loading -> LoadingView(modifier = Modifier.align(Alignment.Center))
            is DetailUiState.Success -> { DetailContent(listing = (uiState as DetailUiState.Success).listing) }
        }
    }
}

@Composable
fun DetailContent(listing: ListingModel) {

}