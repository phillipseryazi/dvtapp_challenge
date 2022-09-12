package com.mudhut.dvtappchallenge.domain

import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import com.mudhut.dvtappchallenge.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    fun getCurrentWeather(lat: Double, lon: Double): Flow<Resource<CurrentWeather>>
    fun getWeatherForecast(lat: Double, lon: Double): Flow<Resource<WeatherForecast>>
    suspend fun insertCurrentWeather(weather: CurrentWeather)
    suspend fun insertWeatherForecast(forecast: WeatherForecast)
    fun getLatestCurrentWeather(): Flow<Resource<CurrentWeather?>>
    fun getLatestWeatherForecast(): Flow<Resource<WeatherForecast?>>
    suspend fun getAllLocations(): List<CurrentWeather>
    suspend fun getFavoriteLocations(): List<CurrentWeather>
    suspend fun favoriteLocation(id: Long)
}
