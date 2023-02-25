package com.semba.realestateapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponse (
    @SerialName("items") val items: List<ListingItem>? = null,
    @SerialName("totalCount") val totalCount: Int? = null,
        )