package com.semba.realestateapp.data.remote.datasource

import com.semba.realestateapp.data.model.*
import com.semba.realestateapp.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService): RemoteDataSource {

    override suspend fun fetchListings(): DataResponse<List<ListingModel>> = withContext(Dispatchers.IO) {
        try {
            val result = apiService.getListings()
            if (result.isSuccessful) {
                Timber.d("Http request is successful. Items size = ${result.body()?.items?.size}")
                val items = result.body()?.items?.map { it.toModel() } ?: emptyList()
                DataResponse.Success(items)
            } else {
                val errorCode = result.code()
                Timber.d("Http request is failed. ErrorCode is = $errorCode")
                DataResponse.Failure(errorCode)
            }
        } catch (e: Exception) {
            Timber.d("Error while fetching listings = ${e.message}")
            DataResponse.Failure(-1)
        }
    }

    override suspend fun fetchListingDetail(listingId: Int): DataResponse<ListingModel> = withContext(
        Dispatchers.IO) {
        try {
            val result = apiService.getListingDetail(listingId)
            if (result.isSuccessful && result.body() != null) {
                Timber.d("Http request is successful. Item fetched with id ${listingId}}")
                val item = result.body()!!.toModel()
                DataResponse.Success(item)
            } else {
                val errorCode = result.code()
                Timber.d("Http request is failed. ErrorCode is = $errorCode")
                DataResponse.Failure(errorCode)
            }
        } catch (e: Exception) {
            Timber.d("Error while fetching listing detail = ${e.message}")
            DataResponse.Failure(500)
        }
    }
}