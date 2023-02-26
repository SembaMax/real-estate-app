package com.semba.realestateapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.design.theme.RealEstateAppTheme
import com.semba.realestateapp.feature.home.ui.HomeScreen
import com.semba.realestateapp.feature.home.ui.HomeScreenUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    lateinit var fakeUiState: HomeScreenUiState

    @Before
    fun init() {
        fakeUiState = HomeScreenUiState.Loading
    }

    @Test
    fun testLoadingUIState() {
        fakeUiState = HomeScreenUiState.Loading
        val loadingString = composeTestRule.activity.getString(R.string.loading)

        composeTestRule.setContent {
            RealEstateAppTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithText(loadingString).assertIsDisplayed()
    }

    @Test
    fun testFailureUIState() {
        fakeUiState = HomeScreenUiState.Error(-1)
        val errorString = composeTestRule.activity.getString(R.string.error)

        composeTestRule.setContent {
            RealEstateAppTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithText(errorString).assertIsDisplayed()
    }

    @Test
    fun testSuccessUIState() {
        fakeUiState = HomeScreenUiState.Success(listings)

        val loadingString = composeTestRule.activity.getString(R.string.loading)
        val errorString = composeTestRule.activity.getString(R.string.error)

        composeTestRule.setContent {
            RealEstateAppTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithText(loadingString).assertDoesNotExist()
        composeTestRule.onNodeWithText(errorString).assertDoesNotExist()
        composeTestRule.onNodeWithTag("listings_lazy_column").assertExists()
    }

    @Test
    fun testLazyColumnDisplaysCorrectData() {
        fakeUiState = HomeScreenUiState.Success(listings)

        val meterString = composeTestRule.activity.getString(R.string.meter_square)
        val currencyString = composeTestRule.activity.getString(R.string.euro_sign)
        val roomString = composeTestRule.activity.getString(R.string.room)

        composeTestRule.setContent {
            RealEstateAppTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        val firstItem = composeTestRule.onNodeWithTag("listings_lazy_column").onChildAt(0)
        firstItem.assertIsDisplayed()
        firstItem.assert(hasText("Berlin"))
        firstItem.assert(hasText("Company"))
        firstItem.assert(hasText("Apartment"))
        firstItem.assert(hasText("10 ${meterString}",))
        firstItem.assert(hasText("1 ${roomString}",))
        firstItem.assert(hasText("${currencyString}100"))
    }

    @Test
    fun testTopBarIsDisplayedOnSuccessUIState() {
        fakeUiState = HomeScreenUiState.Success()

        val headerTitle = composeTestRule.activity.getString(R.string.header_title)

        composeTestRule.setContent {
            RealEstateAppTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithText(headerTitle).assertIsDisplayed()
    }

    @Test
    fun testCollapsingTopBarScrollBehavior() {
        fakeUiState = HomeScreenUiState.Success(listings)

        val headerTitle = composeTestRule.activity.getString(R.string.header_title)

        composeTestRule.setContent {
            RealEstateAppTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithTag("listings_lazy_column").performScrollToIndex(3)
        composeTestRule.onNodeWithText(headerTitle).assertHeightIsAtLeast(1.dp)
    }

    private val listings = listOf(
        ListingModel(1,1,1,1,"Berlin",10.0,100.0,"","Company","Apartment"),
        ListingModel(2,2,2,2,"Hamburg",20.0,200.0,"","",""),
        ListingModel(3,3,3,3,"Bonn",30.0,300.0,"","",""),
        ListingModel(4,4,4,4,"Frankfurt",40.0,400.0,"","",""),
    )

}