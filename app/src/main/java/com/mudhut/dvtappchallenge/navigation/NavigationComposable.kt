package com.mudhut.dvtappchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mudhut.dvtappchallenge.ui.viewmodels.*
import com.mudhut.dvtappchallenge.ui.views.FavoritesScreen
import com.mudhut.dvtappchallenge.ui.views.HomeScreen
import com.mudhut.dvtappchallenge.ui.views.MapScreen
import com.mudhut.dvtappchallenge.utils.FAVORITES_SCREEN
import com.mudhut.dvtappchallenge.utils.HOME_SCREEN
import com.mudhut.dvtappchallenge.utils.MAP_SCREEN

@Composable
fun NavigationComposable() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN,
        builder = {
            composable(HOME_SCREEN) {
                val viewModel = hiltViewModel<HomeScreenViewModel>()
                HomeScreen(
                    uiEvents = HomeScreenUIEvents(
                        onNavigate = { route ->
                            navController.navigate(route)
                        },
                        showDialog = {
                            viewModel.showDialog()
                        },
                        hideDialog = {
                            viewModel.hideDialog()
                        },
                        getCurrentLocation = {
                            viewModel.getCurrentLocation()
                        },
                        favoriteLocation = {
                            viewModel.favoriteLocation(it)
                        }
                    ),
                    uiState = viewModel.homeScreenUIState.collectAsState().value
                )
            }
            composable(MAP_SCREEN) {
                val viewModel = hiltViewModel<MapScreenViewModel>()
                MapScreen(
                    uiEvents = MapScreenUIEvents(
                        onNavigate = {
                            navController.popBackStack()
                        },
                        getAllLocations = {
                            viewModel.getAllLocations()
                        }
                    ),
                    uiState = viewModel.mapScreenUIState.collectAsState().value
                )
            }
            composable(FAVORITES_SCREEN) {
                val viewModel = hiltViewModel<FavoritesScreenViewModel>()
                FavoritesScreen(
                    uiEvents = FavoritesScreenUIEvents(
                        onNavigate = {
                            navController.popBackStack()
                        },
                        getFavoriteLocations = {
                            viewModel.getFavoriteLocations()
                        }
                    ),
                    uiState = viewModel.favoritesScreenUIState.collectAsState().value
                )
            }
        }
    )
}
