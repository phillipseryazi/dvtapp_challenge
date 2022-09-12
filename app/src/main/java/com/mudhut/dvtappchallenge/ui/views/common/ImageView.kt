package com.mudhut.dvtappchallenge.ui.views.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageView(
    image: Int,
    modifier: Modifier
) {
    GlideImage(
        modifier = modifier,
        imageModel = image,
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
    )
}
