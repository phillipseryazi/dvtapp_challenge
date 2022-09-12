package com.mudhut.dvtappchallenge.domain

import com.mudhut.dvtappchallenge.data.local.ILocalDataSource
import com.mudhut.dvtappchallenge.data.remote.IRemoteDataSource
import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import com.mudhut.dvtappchallenge.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IWeatherRepository {
    override fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<CurrentWeather>> = flow {
        remoteDataSource.getCurrentWeather(lat, lon).collect {
            emit(it)
        }
    }

    override fun getWeatherForecast(
        lat: Double,
        lon: Double
    ): Flow<Resource<WeatherForecast>> = flow {
        remoteDataSource.getWeatherForecast(lat, lon).collect {
            emit(it)
        }
    }

    override suspend fun insertCurrentWeather(weather: CurrentWeather) {
        localDataSource.insertCurrentWeather(weather)
    }

    override suspend fun insertWeatherForecast(forecast: WeatherForecast) {
        localDataSource.insertWeatherForecast(forecast)
    }

    override fun getLatestCurrentWeather(): Flow<Resource<CurrentWeather?>> {
        return localDataSource.getLatestCurrentWeather().mapLatest {
            Resource.Success(data = it)
        }
    }

    override fun getLatestWeatherForecast(): Flow<Resource<WeatherForecast?>> {
        return localDataSource.getLatestWeatherForecast().mapLatest {
            Resource.Success(data = it)
        }
    }

    override suspend fun getAllLocations(): List<CurrentWeather> {
        return localDataSource.getAllLocations()
    }

    override suspend fun getFavoriteLocations(): List<CurrentWeather> {
        return localDataSource.getFavoriteLocations()
    }

    override suspend fun favoriteLocation(id: Long) {
        localDataSource.favoriteLocation(id)
    }
}
