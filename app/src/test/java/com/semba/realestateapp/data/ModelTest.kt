package com.semba.realestateapp.data

import com.semba.realestateapp.data.model.ListingItem
import com.semba.realestateapp.data.model.toModel
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ModelTest {

    @Test
    fun `map listing item to listing model`() {
        val listingItem = ListingItem(
            id = 1,
            offerType = 1,
            rooms = 5,
            bedrooms = 2,
            city = "Berlin",
            area = 100.0,
            price = 200000.0,
            url = "url",
            professional = "professional",
            propertyType = "propertyType",
        )

        val listingModel = listingItem.toModel()

        assertEquals(1, listingModel.id)
        assertEquals(1, listingModel.offerType)
        assertEquals(5, listingModel.rooms)
        assertEquals(2, listingModel.bedrooms)
        assertEquals("Berlin", listingModel.city)
        assertEquals(100.0, listingModel.area)
        assertEquals(200000.0, listingModel.price)
        assertEquals("url", listingModel.imageUrl)
        assertEquals("professional", listingModel.professional)
        assertEquals("propertyType", listingModel.propertyType)
    }
}