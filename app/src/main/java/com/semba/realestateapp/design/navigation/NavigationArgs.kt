package com.semba.realestateapp.design.navigation

import timber.log.Timber

const val LISTING_ID_ARG = "listingId"

fun String.withArgs(args: Map<String, String>): String {
    var result = this
    args.forEach { (key, value) ->
        result = result.replace("{$key}", value)
    }
    Timber.d("Detail screen route with args: $result")
    return result
}