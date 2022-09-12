package com.mudhut.dvtappchallenge.di

import com.mudhut.dvtappchallenge.data.local.ILocalDataSource
import com.mudhut.dvtappchallenge.data.local.LocalDataSource
import com.mudhut.dvtappchallenge.data.remote.IRemoteDataSource
import com.mudhut.dvtappchallenge.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindRemoteDataSource(dataSource: RemoteDataSource): IRemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(dataSource: LocalDataSource): ILocalDataSource
}
