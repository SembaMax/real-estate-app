package com.semba.realestateapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingItem (
    @SerialName("id") val id: Int? = null,
    @SerialName("offerType") val offerType: Int? = null,
    @SerialName("rooms") val rooms: Int? = null,
    @SerialName("bedrooms") val bedrooms: Int? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("area") val area: Double? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("professional") val professional: String? = null,
    @SerialName("propertyType") val propertyType: String? = null,
)

fun ListingItem.toModel(): ListingModel =
    ListingModel(
        id = this.id,
        offerType = this.offerType,
        rooms = this.rooms,
        bedrooms = this.bedrooms,
        city = this.city,
        area = this.area,
        price = this.price,
        imageUrl = this.url,
        professional = this.professional,
        propertyType = this.propertyType,
    )