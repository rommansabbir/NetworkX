package com.rommansabbir.networkx.dialog.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rommansabbir.networkx.R
import com.rommansabbir.networkx.ui.theme.Green


@Composable
fun Padding(value: Int) = Spacer(modifier = Modifier.padding(value.dp))

@Composable
fun EmptySquareButton(title: String = "") {
    IconButton(onClick = { }) {
        Icon(
            ImageVector.Builder(
                title,
                defaultHeight = 0.dp,
                defaultWidth = 0.dp,
                viewportHeight = 0F,
                viewportWidth = 0F
            ).build(), contentDescription = ""
        )
    }
}

fun defaultNoInternetUI() = NoInternetUI(
    headerTitle = "No Internet",
    headerLogo = R.drawable.ic_baseline_cloud_off_24,
    message = "Device is not connected to the internet. Make sure your device is connected to the internet.",
    actionBtnTitle = "OK",
    true,
    UIStyle = UIStyle(
        Color.White,
        Color.Black,
        Green,
        Color.White
    ),
    buttonStyle = ButtonStyle(
        Color.White,
        Green
    )
)