package com.mudhut.dvtappchallenge.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.data.remote.models.WeatherForecast
import com.mudhut.dvtappchallenge.domain.IWeatherRepository
import com.mudhut.dvtappchallenge.domain.location.ILocationTracker
import com.mudhut.dvtappchallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenUIState(
    var showDialog: Boolean = false,
    var isLoading: Boolean = false,
    var currentWeather: CurrentWeather? = null,
    var weatherForecast: WeatherForecast? = null
)

data class HomeScreenUIEvents(
    val onNavigate: (route: String) -> Unit,
    val showDialog: () -> Unit,
    val hideDialog: () -> Unit,
    val getCurrentLocation: () -> Unit,
    val favoriteLocation: (id: Long) -> Unit,
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: IWeatherRepository,
    private val locationTracker: ILocationTracker
) : ViewModel() {
    private val TAG = "HomeScreenViewModel"

    var homeScreenUIState = MutableStateFlow(HomeScreenUIState())
        private set

    init {
        getLatestCurrentWeather()
        getLatestWeatherForecast()
    }

    fun showDialog() {
        homeScreenUIState.update {
            it.copy(showDialog = true)
        }
    }

    fun hideDialog() {
        homeScreenUIState.update {
            it.copy(showDialog = false)
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let {
                getCurrentWeatherUpdate(it.latitude, it.longitude)
                getForecastWeatherUpdate(it.latitude, it.longitude)
            }
        }
    }

    private fun getCurrentWeatherUpdate(lat: Double, lon: Double) {
        viewModelScope.launch {
            repository.getCurrentWeather(lat, lon).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        resource.data?.let { currentWeather ->
                            repository.insertCurrentWeather(currentWeather)
                        }
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "getCurrentWeatherUpdate: ${resource.message}")
                    }
                }
            }
        }
    }

    private fun getForecastWeatherUpdate(lat: Double, lon: Double) {
        viewModelScope.launch {
            repository.getWeatherForecast(lat, lon).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        resource.data?.let { forecast ->
                            repository.insertWeatherForecast(forecast)
                        }
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "getForecastWeatherUpdate: ${resource.message}")
                    }
                }
            }
        }
    }

    private fun getLatestCurrentWeather() {
        viewModelScope.launch {
            repository.getLatestCurrentWeather().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        homeScreenUIState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        resource.data?.let { currentWeather ->
                            homeScreenUIState.update {
                                it.copy(
                                    isLoading = false,
                                    currentWeather = currentWeather
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        homeScreenUIState.update {
                            it.copy(isLoading = false)
                        }
                        Log.e(TAG, "getLatestCurrentWeather: ${resource.message}")
                    }
                }
            }
        }
    }

    private fun getLatestWeatherForecast() {
        viewModelScope.launch {
            repository.getLatestWeatherForecast().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        resource.data?.let { weatherForecast ->
                            homeScreenUIState.update {
                                it.copy(
                                    weatherForecast = weatherForecast
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "getLatestWeatherForecast: ${resource.message}")
                    }
                }
            }
        }
    }

    fun favoriteLocation(id: Long) {
        viewModelScope.launch {
            repository.favoriteLocation(id)
        }
    }
}
