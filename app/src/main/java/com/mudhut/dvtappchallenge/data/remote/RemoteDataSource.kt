package com.mudhut.dvtappchallenge.data.remote

import android.util.Log
import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import com.mudhut.dvtappchallenge.data.remote.retrofit.APIService
import com.mudhut.dvtappchallenge.utils.APP_ID
import com.mudhut.dvtappchallenge.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: APIService
) : IRemoteDataSource {
    private val TAG = "RemoteDataSource"

    override fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Loading())

        val response = service.getCurrentWeather(
            lat.toString(),
            lon.toString(),
            APP_ID
        )

        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
            Log.d(TAG, "getCurrentWeather: ${response.body()}")
        }
    }.catch {
        emit(Resource.Error(message = "Current weather api call was unsuccessful"))
    }

    override fun getWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<WeatherForecast>> = flow {
        emit(Resource.Loading())

        val response = service.getWeatherForecast(
            lat.toString(),
            lon.toString(),
            APP_ID
        )

        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        }
    }.catch {
        emit(Resource.Error(message = "Weather forecast api call was unsuccessful"))
    }
}
