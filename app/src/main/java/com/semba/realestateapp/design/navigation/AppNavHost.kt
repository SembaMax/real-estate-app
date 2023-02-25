package com.semba.realestateapp.design.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.semba.realestateapp.feature.detail.ui.detailScreen
import com.semba.realestateapp.feature.home.ui.homeRoute
import com.semba.realestateapp.feature.home.ui.homeScreen

@Composable
fun AppNavHost (
    navController: NavHostController,
    onBackClick: () -> Unit,
    navigateTo: (screenDestination: ScreenDestination, args: Map<String, String>) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute
)
{
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        homeScreen(navigateTo)
        detailScreen()
    }
}