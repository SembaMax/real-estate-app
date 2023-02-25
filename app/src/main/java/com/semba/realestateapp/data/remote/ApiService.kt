package com.semba.realestateapp.data.remote

import com.semba.realestateapp.data.model.ListingItem
import com.semba.realestateapp.data.model.ListingsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Routes.LISTINGS_ENDPOINT)
    suspend fun getListings(): Response<ListingsResponse>

    @GET(Routes.LISTING_DETAIL_ENDPOINT)
    suspend fun getListingDetail(@Path("id") id: Int): Response<ListingItem>
}