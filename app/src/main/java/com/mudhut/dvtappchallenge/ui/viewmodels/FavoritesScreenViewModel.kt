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

data class FavoritesScreenUIState(
    var favorites: List<CurrentWeather> = emptyList()
)

data class FavoritesScreenUIEvents(
    val onNavigate: () -> Unit,
    val getFavoriteLocations: () -> Unit
)

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val repository: IWeatherRepository,
) : ViewModel() {
    var favoritesScreenUIState = MutableStateFlow(FavoritesScreenUIState())
        private set

    fun getFavoriteLocations() {
        viewModelScope.launch {
            favoritesScreenUIState.update {
                it.copy(favorites = repository.getFavoriteLocations())
            }
        }
    }
}
