package com.mudhut.dvtappchallenge.ui.views.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InformationRow(
    leftInfo: @Composable () -> Unit,
    centerInfo: @Composable () -> Unit,
    rightInfo: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leftInfo()
            centerInfo()
            rightInfo()
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}

@Preview
@Composable
fun InformationRowPreview() {
    InformationRow(
        leftInfo = {
            Text(
                text = "18 \u00B0\n min",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                )
            )
        },
        centerInfo = {
            Text(
                text = "18 \u00B0\n Current",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                )
            )
        },
        rightInfo = {
            Text(
                text = "18 \u00B0\n max",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                )
            )
        })
}
