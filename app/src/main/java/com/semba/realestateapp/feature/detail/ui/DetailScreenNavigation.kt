package com.semba.realestateapp.feature.detail.ui

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.semba.realestateapp.design.navigation.LISTING_ID_ARG
import com.semba.realestateapp.design.navigation.withArgs

const val detailRoute = "detail_screen_route/{$LISTING_ID_ARG}"

fun NavController.navigateToDetailScreen(args: Map<String, String>, navOptions: NavOptions? = null) {
    this.navigate(detailRoute.withArgs(args), navOptions)
}

private val args = listOf(
    navArgument(LISTING_ID_ARG) {
        type = NavType.IntType

    },
)

fun NavGraphBuilder.detailScreen() {
    composable(route = detailRoute, arguments = args) { navBackStackEntry ->
        val listingId = navBackStackEntry.arguments?.getInt(LISTING_ID_ARG) ?: 0
        DetailRoute(listingId = listingId)
    }
}