package com.mudhut.dvtappchallenge.ui.views.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mudhut.dvtappchallenge.ui.theme.Cloudy

@Composable
fun PermissionDialog(
    promptString: String,
    permissionString: String,
    onDenyPermission: () -> Unit,
    onGrantPermission: () -> Unit
) {

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "$promptString\n$permissionString",
                    modifier = Modifier
                        .fillMaxWidth(),

                    style = TextStyle(
                        color = Cloudy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DVTButton(
                        modifier = Modifier.padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Cloudy
                        ),
                        shape = RoundedCornerShape(8.dp),
                        content = {
                            Text(
                                "DENY PERMISSIONS",
                                modifier = Modifier.padding(
                                    top = 12.dp,
                                    bottom = 12.dp,
                                    start = 12.dp,
                                    end = 12.dp
                                ),
                                style = TextStyle(
                                    color = Cloudy,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        },
                        onButtonClicked = onDenyPermission
                    )

                    DVTButton(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Cloudy,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        content = {
                            Text(
                                "GRANT PERMISSIONS",
                                modifier = Modifier.padding(
                                    top = 12.dp,
                                    bottom = 12.dp,
                                    start = 12.dp,
                                    end = 12.dp
                                ),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                ),
                                textAlign = TextAlign.Center
                            )
                        },
                        onButtonClicked = onGrantPermission
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Preview
@Composable
fun PermissionDialogPreview() {
    PermissionDialog(
        promptString = "For this app to work properly please grant these permissions",
        permissionString = "Location",
        onDenyPermission = {},
        onGrantPermission = {},
    )
}
