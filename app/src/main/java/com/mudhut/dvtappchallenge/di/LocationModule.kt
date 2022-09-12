package com.mudhut.dvtappchallenge.di

import com.mudhut.dvtappchallenge.domain.location.ILocationTracker
import com.mudhut.dvtappchallenge.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    abstract fun bindLocationTracker(locationTracker: LocationTracker): ILocationTracker
}
