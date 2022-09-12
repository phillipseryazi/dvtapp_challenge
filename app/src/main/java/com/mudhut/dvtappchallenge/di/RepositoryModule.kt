package com.mudhut.dvtappchallenge.di

import com.mudhut.dvtappchallenge.domain.IWeatherRepository
import com.mudhut.dvtappchallenge.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(repository: WeatherRepository): IWeatherRepository
}
