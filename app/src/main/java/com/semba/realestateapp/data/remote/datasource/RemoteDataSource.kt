package com.semba.realestateapp.data.remote.datasource

import com.semba.realestateapp.data.model.DataResponse
import com.semba.realestateapp.data.model.ListingModel

interface RemoteDataSource {
    suspend fun fetchListings(): DataResponse<List<ListingModel>>
    suspend fun fetchListingDetail(listingId: Int): DataResponse<ListingModel>
}