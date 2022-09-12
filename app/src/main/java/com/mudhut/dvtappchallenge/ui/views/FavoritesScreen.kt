package com.mudhut.dvtappchallenge.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.dvtappchallenge.R
import com.mudhut.dvtappchallenge.data.remote.models.CurrentWeather
import com.mudhut.dvtappchallenge.ui.theme.Cloudy
import com.mudhut.dvtappchallenge.ui.theme.Rainy
import com.mudhut.dvtappchallenge.ui.theme.Sunny
import com.mudhut.dvtappchallenge.ui.viewmodels.FavoritesScreenUIEvents
import com.mudhut.dvtappchallenge.ui.viewmodels.FavoritesScreenUIState
import com.mudhut.dvtappchallenge.ui.views.common.AppBar
import com.mudhut.dvtappchallenge.ui.views.common.ImageView

@Composable
fun FavoritesScreen(
    uiEvents: FavoritesScreenUIEvents,
    uiState: FavoritesScreenUIState
) {
    LaunchedEffect(key1 = null, block = {
        uiEvents.getFavoriteLocations()
    })
    Column(modifier = Modifier.fillMaxSize()) {
        AppBar(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.text_favorites),
            backgroundColor = Color.Transparent,
            contentColor = Cloudy,
            icon = Icons.Default.ArrowBack,
            onNavigationIconClick = { uiEvents.onNavigate() }
        )
        if (uiState.favorites.isNotEmpty()) {
            LazyColumn {
                itemsIndexed(uiState.favorites) { _, it ->
                    FavoriteCard(it)
                }
            }
        } else {
            Text(
                stringResource(R.string.text_no_favorites),
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                style = TextStyle(textAlign = TextAlign.Center)
            )
        }
    }
}

@Composable
fun FavoriteCard(weather: CurrentWeather) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp
                ),
            elevation = 4.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(8.dp)
                )
                Surface(
                    shape = CircleShape,
                    color = when (weather.weather[0].main) {
                        "Rain" -> Rainy
                        "Clouds" -> Cloudy
                        else -> Sunny
                    }
                ) {
                    ImageView(
                        image = when (weather.weather[0].main) {
                            "Clouds" -> R.drawable.cloudy
                            "Rain" -> R.drawable.rainy
                            else -> R.drawable.clear
                        }, modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                    )
                    Text(
                        text = weather.name,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Cloudy,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = weather.weather[0].main,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Cloudy.copy(alpha = 0.5f),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
    }
}


@Preview
@Composable
fun FavoritesScreenPreview() {
}
