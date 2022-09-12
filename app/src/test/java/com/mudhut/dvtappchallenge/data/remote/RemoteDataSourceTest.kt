package com.mudhut.dvtappchallenge.data.remote

import com.google.common.truth.Truth.assertThat
import com.mudhut.dvtappchallenge.data.remote.retrofit.APIService
import com.mudhut.dvtappchallenge.enqueueResponse
import com.mudhut.dvtappchallenge.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {

    private val mockWebServer = MockWebServer()

    private lateinit var dataSource: RemoteDataSource
    private lateinit var retrofitInstance: APIService
    private lateinit var client: OkHttpClient

    @Before
    fun setUp() {
        mockWebServer.start(8080)

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        retrofitInstance = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

        dataSource = RemoteDataSource(
            retrofitInstance
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `test get current weather`() = runTest {
        mockWebServer.enqueueResponse("current_weather.json", 200)
        dataSource.getCurrentWeather(12.00, 13.00).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    assertThat(it.data?.id).isEqualTo(231139)
                }
                is Resource.Error -> {}
            }
        }
    }

    @Test
    fun `test get weather forecast`() = runTest {
        mockWebServer.enqueueResponse("weather_forecast.json", 200)
        dataSource.getWeatherForecast(12.00, 13.00).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    assertThat(it.data?.city?.population).isEqualTo(17947)
                }
                is Resource.Error -> {}
            }
        }
    }
}
