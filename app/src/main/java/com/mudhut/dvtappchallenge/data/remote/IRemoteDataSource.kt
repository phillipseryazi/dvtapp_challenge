package com.mudhut.dvtappchallenge.data.remote

import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import com.mudhut.dvtappchallenge.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    fun getCurrentWeather(lat: Double, lon: Double): Flow<Resource<CurrentWeather>>
    fun getWeatherForecast(lat: Double, lon: Double): Flow<Resource<WeatherForecast>>
}
