package com.mudhut.dvtappchallenge.ui.views

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mudhut.dvtappchallenge.R
import com.mudhut.dvtappchallenge.ui.models.MenuItem
import com.mudhut.dvtappchallenge.ui.theme.Cloudy
import com.mudhut.dvtappchallenge.ui.theme.Rainy
import com.mudhut.dvtappchallenge.ui.theme.Sunny
import com.mudhut.dvtappchallenge.ui.viewmodels.HomeScreenUIEvents
import com.mudhut.dvtappchallenge.ui.viewmodels.HomeScreenUIState
import com.mudhut.dvtappchallenge.ui.views.common.*
import com.mudhut.dvtappchallenge.utils.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    uiEvents: HomeScreenUIEvents,
    uiState: HomeScreenUIState
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
        onDispose {}
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionsList = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissionsList
    )

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            PermissionDialog(
                promptString = "For this app to work properly please grant these permissions",
                permissionString = "Location",
                onDenyPermission = {
                    uiEvents.hideDialog()
                },
                onGrantPermission = {
                    permissionState.launchMultiplePermissionRequest()
                    uiEvents.hideDialog()
                })
        },
        permissionsNotAvailableContent = {}) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerBackgroundColor = when (uiState.currentWeather?.weather?.get(0)?.main) {
                "Rain" -> Rainy
                "Clouds" -> Cloudy
                else -> Sunny
            },
            drawerContentColor = Color.White,
            drawerContent = {
                Column() {
                    Spacer(modifier = Modifier.height(24.dp))
                    DrawerBody(items = listOf(
                        MenuItem(
                            id = HOME_LOWER_CASE,
                            title = HOME_UPPER_CASE,
                            contentDescription = "Go to home screen",
                            icon = Icons.Default.Home
                        ),
                        MenuItem(
                            id = MAP_LOWER_CASE,
                            title = MAP_UPPER_CASE,
                            contentDescription = "Go to map screen",
                            icon = Icons.Default.LocationOn
                        ),
                        MenuItem(
                            id = FAVORITES_LOWER_CASE,
                            title = FAVORITES_UPPER_CASE,
                            contentDescription = "Go to favorites screen",
                            icon = Icons.Default.Favorite
                        )
                    ), onItemClick = {
                        when (it.id) {
                            HOME_LOWER_CASE -> {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                                uiEvents.onNavigate(HOME_SCREEN)
                            }
                            MAP_LOWER_CASE -> {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                                uiEvents.onNavigate(MAP_SCREEN)
                            }
                            FAVORITES_LOWER_CASE -> {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                                uiEvents.onNavigate(FAVORITES_SCREEN)

                            }
                        }
                    })
                }
            },
            content = {
                DisposableEffect(lifecycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            uiEvents.getCurrentLocation()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    HomeScreenContent(
                        modifier = Modifier.padding(it),
                        uiEvents = uiEvents,
                        uiState = uiState
                    )
                    AppBar(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 24.dp),
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White,
                        icon = Icons.Default.Menu,
                        onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    uiEvents: HomeScreenUIEvents,
    uiState: HomeScreenUIState
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f, fill = true)
        ) {
            ImageView(
                image = when (uiState.currentWeather?.weather?.get(0)?.main) {
                    "Rain" -> R.drawable.forest_rainy
                    "Clouds" -> R.drawable.forest_cloudy
                    else -> R.drawable.forest_sunny
                },
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "${uiState.currentWeather?.main?.temp}\u00B0\n${
                    uiState.currentWeather?.weather?.get(
                        0
                    )?.main
                }",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 32.sp,
                    color = Color.White
                )
            )
            DVTButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_favorite),
                        contentDescription = null
                    )
                },
                onButtonClicked = {
                    uiState.currentWeather?.let { uiEvents.favoriteLocation(it.id) }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1f,
                    fill = true
                )
                .background(
                    color = when (uiState.currentWeather?.weather?.get(0)?.main) {
                        "Rain" -> Rainy
                        "Clouds" -> Cloudy
                        else -> Sunny
                    }
                )
        ) {
            InformationRow(
                leftInfo = {
                    Text(
                        text = "${uiState.currentWeather?.main?.tempMin}\u00B0\nmin",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                },
                centerInfo = {
                    Text(
                        text = "${uiState.currentWeather?.main?.temp}\u00B0\nCurrent",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                },
                rightInfo = {
                    Text(
                        text = "${uiState.currentWeather?.main?.tempMax}\u00B0\nmax",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                }
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                thickness = 2.dp
            )
            uiState.weatherForecast?.list?.distinctBy { it.dtTxt.split(" ")[0] }?.forEach {
                InformationRow(
                    leftInfo = {
                        Text(
                            text = formatDate(it.dt),
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                    },
                    centerInfo = {
                        ImageView(
                            image = when (it.weather[0].main) {
                                "Clouds" -> R.drawable.cloudy
                                "Rain" -> R.drawable.rainy
                                else -> R.drawable.clear
                            },
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    rightInfo = {
                        Text(
                            text = "${it.main.temp}\u00B0",
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                    })
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiEvents = HomeScreenUIEvents(
            onNavigate = {},
            showDialog = {},
            hideDialog = {},
            getCurrentLocation = {},
            favoriteLocation = {}
        ),
        uiState = HomeScreenUIState(
            showDialog = false
        )
    )
}
