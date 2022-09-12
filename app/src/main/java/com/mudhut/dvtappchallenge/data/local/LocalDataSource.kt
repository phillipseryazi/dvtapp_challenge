package com.mudhut.dvtappchallenge.data.local

import com.mudhut.dvtappchallenge.data.local.db.CurrentWeatherEntity
import com.mudhut.dvtappchallenge.data.local.db.WeatherDao
import com.mudhut.dvtappchallenge.data.local.db.WeatherForecastEntity
import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSource @Inject constructor(
    private val dao: WeatherDao
) : ILocalDataSource {
    override suspend fun insertCurrentWeather(weather: CurrentWeather) {
        dao.insertCurrentWeather(
            CurrentWeatherEntity(
                id = weather.id,
                weather = weather,
                createdAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun insertWeatherForecast(forecast: WeatherForecast) {
        dao.insertWeatherForecast(
            WeatherForecastEntity(
                id = forecast.city.id,
                forecast = forecast,
                createdAt = System.currentTimeMillis()
            )
        )
    }

    override fun getLatestCurrentWeather(): Flow<CurrentWeather?> {
        return dao.getLatestCurrentWeather().mapLatest { it?.weather }
    }

    override fun getLatestWeatherForecast(): Flow<WeatherForecast?> {
        return dao.getLatestWeatherForecast().mapLatest { it?.forecast }
    }

    override suspend fun getAllLocations(): List<CurrentWeather> {
        return dao.getAllLocations().mapNotNull { it.weather }
    }

    override suspend fun getFavoriteLocations(): List<CurrentWeather> {
        return dao.getFavoriteLocations().mapNotNull { it.weather }
    }

    override suspend fun favoriteLocation(id: Long) {
        dao.favoriteLocation(id)
    }
}
