package com.mudhut.dvtappchallenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mudhut.dvtappchallenge.data.local.db.DVTDatabase
import com.mudhut.dvtappchallenge.data.local.db.WeatherDao
import com.mudhut.dvtappchallenge.data.remote.retrofit.APIService
import com.mudhut.dvtappchallenge.utils.BASE_URL
import com.mudhut.dvtappchallenge.utils.DVT_DATABASE_NAME
import com.mudhut.dvtappchallenge.utils.NetworkAvailabilityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesNetworkInterceptor(@ApplicationContext context: Context) =
        NetworkAvailabilityInterceptor(context)

    @Singleton
    @Provides
    fun providesHttpClient(networkInterceptor: NetworkAvailabilityInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesAPIService(client: OkHttpClient): APIService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun providesFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Singleton
    @Provides
    fun providesDVTDatabase(@ApplicationContext context: Context): DVTDatabase {
        return Room.databaseBuilder(
            context,
            DVTDatabase::class.java,
            DVT_DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesWeatherDao(database: DVTDatabase): WeatherDao {
        return database.getWeatherDao()
    }
}
