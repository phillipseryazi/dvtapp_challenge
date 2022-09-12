package com.mudhut.dvtappchallenge.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeatherForecast(forecast: WeatherForecastEntity)

    @Query("SELECT *, MAX(createdAt) FROM CurrentWeatherEntity")
    fun getLatestCurrentWeather(): Flow<CurrentWeatherEntity?>

    @Query("SELECT *, MAX(createdAt) FROM WeatherForecastEntity")
    fun getLatestWeatherForecast(): Flow<WeatherForecastEntity?>

    @Query("SELECT * FROM CurrentWeatherEntity")
    suspend fun getAllLocations(): List<CurrentWeatherEntity>

    @Query("SELECT * FROM CurrentWeatherEntity WHERE isFavorite=1")
    suspend fun getFavoriteLocations(): List<CurrentWeatherEntity>

    @Query("UPDATE CurrentWeatherEntity SET isFavorite=1 WHERE id=:id")
    suspend fun favoriteLocation(id: Long)
}
