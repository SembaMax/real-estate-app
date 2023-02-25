package com.semba.realestateapp

import com.semba.realestateapp.data.model.toModel
import com.semba.realestateapp.feature.home.domain.GetListingsUseCase
import com.semba.realestateapp.feature.home.ui.HomeScreenUiState
import com.semba.realestateapp.feature.home.ui.HomeViewModel
import com.semba.realestateapp.testing.TestListingsRepository
import com.semba.realestateapp.util.MainDispatcherRule
import com.semba.realestateapp.util.Utils
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TestListingsRepository
    private lateinit var getListingsUseCase: GetListingsUseCase
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        repository = TestListingsRepository()
        getListingsUseCase = GetListingsUseCase(repository)
        homeViewModel = HomeViewModel(getListingsUseCase)
    }

    @Test
    fun `state is initially loading`() = runTest {
        assertEquals(HomeScreenUiState.Loading, homeViewModel.uiState.value)
    }

    @Test
    fun `state is success on successful request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        repository.setIsSuccessful(true)
        repository.setListings(emptyList())

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Success)

        collectJob.cancel()
    }

    @Test
    fun `state is error on failure request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        repository.setIsSuccessful(false)
        repository.setListings(emptyList())

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Error)

        collectJob.cancel()
    }

    @Test
    fun `emit correct data on successful request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        val result = Utils.listingsJsonAsItems()
        repository.setIsSuccessful(true)
        repository.setListings(result)

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Success)
        assertEquals(result.map { it.toModel() }, (homeViewModel.uiState.value as HomeScreenUiState.Success).listings)

        collectJob.cancel()
    }

    @Test
    fun `emit correct data on failure request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        repository.setIsSuccessful(false)
        repository.setListings(emptyList())

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Error)
        assertEquals(-1, (homeViewModel.uiState.value as HomeScreenUiState.Error).errorCode)

        collectJob.cancel()
    }

}