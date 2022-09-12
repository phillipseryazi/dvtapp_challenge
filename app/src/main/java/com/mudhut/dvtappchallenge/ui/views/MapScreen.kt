package com.mudhut.dvtappchallenge.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.mudhut.dvtappchallenge.ui.theme.Cloudy
import com.mudhut.dvtappchallenge.ui.viewmodels.MapScreenUIEvents
import com.mudhut.dvtappchallenge.ui.viewmodels.MapScreenUIState
import com.mudhut.dvtappchallenge.ui.views.common.AppBar

@Composable
fun MapScreen(
    uiEvents: MapScreenUIEvents,
    uiState: MapScreenUIState
) {
    LaunchedEffect(key1 = null, block = {
        uiEvents.getAllLocations()
    })
    Box(modifier = Modifier.fillMaxSize()) {
        DVTMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(
                    LatLng(0.0, 0.0),
                    10f
                )
            },
            uiState = uiState
        )
        AppBar(
            title = "Saved Locations",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
            backgroundColor = Color.Transparent,
            contentColor = Cloudy,
            icon = Icons.Default.ArrowBack,
            onNavigationIconClick = { uiEvents.onNavigate() }
        )
    }
}

@Composable
fun DVTMap(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    uiState: MapScreenUIState
) {
    val locations = uiState.locations.map { it.coord }
    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        if (locations.isNotEmpty()) {
            GoogleMap(
                cameraPositionState = cameraPositionState.apply {
                    position = CameraPosition.fromLatLngZoom(
                        LatLng(locations[0].lat, locations[0].lon),
                        24f
                    )
                }
            ) {
                locations.forEach {
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(
                                it.lat,
                                it.lon
                            )
                        )
                    )
                }
            }
        }
    }
}

