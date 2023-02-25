package com.semba.realestateapp.data

import com.semba.realestateapp.data.di.apiService
import com.semba.realestateapp.data.model.DataResponse
import com.semba.realestateapp.data.remote.datasource.NetworkDataSource
import com.semba.realestateapp.util.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    lateinit var mockWebServer: MockWebServer

    private val api by lazy {
        apiService(baseUrl = mockWebServer.url("/"), OkHttpClient())
    }

    private lateinit var remoteDataSource: NetworkDataSource

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        remoteDataSource = NetworkDataSource(api)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get listings list`() = runTest {
        val response = MockResponse()
            .setBody(Utils.listingsJsonAsString())
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val result = api.getListings()

        assert(result.body()?.totalCount == 4)
    }

    @Test
    fun `get listings with success result`() = runTest {
        val response = MockResponse()
            .setBody(Utils.listingsJsonAsString())
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val result = remoteDataSource.fetchListings()

        assert(result is DataResponse.Success && result.data?.size == 4)
    }

    @Test
    fun `get listings with failure result`() = runTest {
        val response = MockResponse()
            .setBody("")
            .setResponseCode(400)

        mockWebServer.enqueue(response)

        val result = remoteDataSource.fetchListings()

        assert(result is DataResponse.Failure && result.errorCode == 400)
    }

}