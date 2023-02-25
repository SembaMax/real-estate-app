package com.semba.realestateapp.testing

import com.semba.realestateapp.data.Repository
import com.semba.realestateapp.data.model.DataResponse
import com.semba.realestateapp.data.model.ListingItem
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.data.model.toModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TestListingsRepository: Repository {

    private val itemsFlow: MutableSharedFlow<List<ListingItem>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)


    private var shouldSuccess = true

    override fun loadListings(): Flow<DataResponse<List<ListingModel>>> {
        return if (shouldSuccess)
            itemsFlow.map { images -> DataResponse.Success(data = images.map { it.toModel()}) }
        else
            flow { emit(DataResponse.Failure(errorCode = -1)) }
    }

    override fun loadListingDetail(listingId: Int): Flow<DataResponse<ListingModel>> {
        return if (shouldSuccess)
            itemsFlow.map { images -> DataResponse.Success(data = images.first().toModel()) }
        else
            flow { emit(DataResponse.Failure(errorCode = -1)) }
    }

    fun setListings(items: List<ListingItem>)
    {
        itemsFlow.tryEmit(items)
    }

    fun setIsSuccessful(isSuccessful: Boolean)
    {
        shouldSuccess = isSuccessful
    }
}