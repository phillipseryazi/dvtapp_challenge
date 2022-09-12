package com.mudhut.dvtappchallenge.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.domain.IWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapScreenUIState(
    var locations: List<CurrentWeather> = emptyList()
)

data class MapScreenUIEvents(
    val onNavigate: () -> Unit,
    val getAllLocations: () -> Unit
)

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val repository: IWeatherRepository,
) : ViewModel() {

    var mapScreenUIState = MutableStateFlow(MapScreenUIState())
        private set

    fun getAllLocations() {
        viewModelScope.launch {
            mapScreenUIState.update {
                it.copy(locations = repository.getAllLocations())
            }
        }
    }
}
