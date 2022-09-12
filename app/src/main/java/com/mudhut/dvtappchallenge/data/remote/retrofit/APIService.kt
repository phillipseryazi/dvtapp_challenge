package com.mudhut.dvtappchallenge.data.remote.retrofit

import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("data/2.5/weather?")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): Response<CurrentWeather>

    @GET("data/2.5/forecast?")
    suspend fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
    ): Response<WeatherForecast>
}
