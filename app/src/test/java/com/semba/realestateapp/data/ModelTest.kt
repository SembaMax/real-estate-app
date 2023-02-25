package com.semba.realestateapp.data

import com.semba.realestateapp.data.model.ListingItem
import com.semba.realestateapp.data.model.toModel
import junit.framework.TestCase
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

        TestCase.assertEquals(1, listingModel.id)
        TestCase.assertEquals(1, listingModel.offerType)
        TestCase.assertEquals(5, listingModel.rooms)
        TestCase.assertEquals(2, listingModel.bedrooms)
        TestCase.assertEquals("Berlin", listingModel.city)
        TestCase.assertEquals(100.0, listingModel.area)
        TestCase.assertEquals(200000.0, listingModel.price)
        TestCase.assertEquals("url", listingModel.imageUrl)
        TestCase.assertEquals("professional", listingModel.professional)
        TestCase.assertEquals("propertyType", listingModel.propertyType)
    }
}