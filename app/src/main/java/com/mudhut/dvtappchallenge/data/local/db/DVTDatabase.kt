package com.mudhut.dvtappchallenge.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CurrentWeatherEntity::class,
        WeatherForecastEntity::class
    ],
    version = 1
)
@TypeConverters(
    CurrentWeatherConverter::class,
    WeatherForecastConverter::class,
    WeatherListConverter::class,
    ListElementListConverter::class,
    ListElementConverter::class,
    WeatherConverter::class,
    CoordConverter::class,
    MainConverter::class,
    WindConverter::class,
    RainConverter::class,
    CloudsConverter::class,
    SysConverter::class
)
abstract class DVTDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}
