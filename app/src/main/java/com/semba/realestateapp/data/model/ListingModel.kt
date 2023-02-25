package com.semba.realestateapp.data.model

data class ListingModel (
    val id: Int? = null,
    val offerType: Int? = null,
    val rooms: Int? = null,
    val bedrooms: Int? = null,
    val city: String? = null,
    val area: Double? = null,
    val price: Double? = null,
    val imageUrl: String? = null,
    val professional: String? = null,
    val propertyType: String? = null,
)
{
    companion object {
        fun empty() = ListingModel(0,0, 0, 0, "",0.0,0.0,"", "")
    }
}