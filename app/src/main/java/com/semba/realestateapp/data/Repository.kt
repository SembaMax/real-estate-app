package com.semba.realestateapp.data

import com.semba.realestateapp.data.model.DataResponse
import com.semba.realestateapp.data.model.ListingModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun loadListings(): Flow<DataResponse<List<ListingModel>>>
    fun loadListingDetail(listingId: Int): Flow<DataResponse<ListingModel>>
}