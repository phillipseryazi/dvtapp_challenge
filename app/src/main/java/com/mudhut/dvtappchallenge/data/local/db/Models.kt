package com.mudhut.dvtappchallenge.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast

@Entity(tableName = "CurrentWeatherEntity")
data class CurrentWeatherEntity(
    @PrimaryKey
    val id: Long,
    val createdAt: Long,
    val isFavorite: Boolean = false,
    val weather: CurrentWeather?
)

@Entity(tableName = "WeatherForecastEntity")
data class WeatherForecastEntity(
    @PrimaryKey
    val id: Long,
    val createdAt: Long,
    val forecast: WeatherForecast?
)
