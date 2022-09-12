package com.mudhut.dvtappchallenge.data.local

import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    suspend fun insertCurrentWeather(weather: CurrentWeather)
    suspend fun insertWeatherForecast(forecast: WeatherForecast)
    fun getLatestCurrentWeather(): Flow<CurrentWeather?>
    fun getLatestWeatherForecast(): Flow<WeatherForecast?>
    suspend fun getAllLocations(): List<CurrentWeather>
    suspend fun getFavoriteLocations(): List<CurrentWeather>
    suspend fun favoriteLocation(id: Long)
}
