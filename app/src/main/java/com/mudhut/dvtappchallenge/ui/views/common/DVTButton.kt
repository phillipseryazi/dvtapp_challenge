package com.mudhut.dvtappchallenge.ui.views.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun DVTButton(
    modifier: Modifier,
    isEnabled: Boolean = true,
    border: BorderStroke? = null,
    colors: ButtonColors,
    shape: Shape,
    content: @Composable () -> Unit,
    onButtonClicked: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = isEnabled,
        colors = colors,
        elevation = null,
        contentPadding = PaddingValues(0.dp),
        shape = shape,
        border = border,
        onClick = onButtonClicked
    ) {
        content()
    }
}