package com.semba.realestateapp.util

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.semba.realestateapp.data.model.ListingItem
import com.semba.realestateapp.data.model.ListingsResponse
import java.io.InputStreamReader

object Utils {

    fun listingsJsonAsString(): String {
        val resourceAsStream = javaClass.classLoader?.getResourceAsStream("ListingsResponse.json")
        val reader = InputStreamReader(resourceAsStream)
        return reader.use { it.readText() }
    }

    fun listingsJsonAsItems(): List<ListingItem> {
        val gson = GsonBuilder().create()
        val jsonString = listingsJsonAsString()
        return gson.fromJson(jsonString, ListingsResponse::class.java).items ?: emptyList()
    }
}