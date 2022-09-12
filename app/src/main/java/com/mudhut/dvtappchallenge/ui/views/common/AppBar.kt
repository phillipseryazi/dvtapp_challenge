package com.mudhut.dvtappchallenge.ui.views.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mudhut.dvtappchallenge.ui.theme.Cloudy

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    backgroundColor: Color,
    contentColor: Color,
    icon: ImageVector,
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Toggle drawer",
                    tint = contentColor
                )
            }
        }
    )
}
