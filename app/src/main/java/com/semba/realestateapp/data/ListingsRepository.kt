package com.semba.realestateapp.data

import com.semba.realestateapp.data.model.DataResponse
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.data.remote.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListingsRepository @Inject constructor(private val remoteDataSource: RemoteDataSource): Repository {

    override fun loadListings(): Flow<DataResponse<List<ListingModel>>> = flow {
        val result = remoteDataSource.fetchListings()
        emit(result)
    }

    override fun loadListingDetail(listingId: Int): Flow<DataResponse<ListingModel>> = flow {
        val result = remoteDataSource.fetchListingDetail(listingId)
        emit(result)
    }
}