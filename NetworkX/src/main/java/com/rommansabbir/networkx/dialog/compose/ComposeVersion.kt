package com.rommansabbir.networkx.dialog.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.rommansabbir.networkx.ui.theme.Green


@Composable
fun APSingleActionDialog(
    dialogState: Boolean = false,
    ui: NoInternetUI,
    onCancelled: () -> Unit = {},
    onButtonPressed: () -> Unit = {},
) {
    val dialogShape = RoundedCornerShape(10.dp)

    // To understand that if the dialog closed manually by client or not
    val dialogClosedManually: Boolean by remember {
        mutableStateOf(false)
    }

    val dialogCloseAction = {
        if (!dialogClosedManually) {
            onCancelled.invoke()
        } else {
            onButtonPressed.invoke()
        }
    }

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                dialogCloseAction.invoke()
            },
            title = null,
            text = null,
            buttons = {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ui.UIStyle?.dialogBackgroundColor ?: Color.White),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .background(ui.UIStyle?.backgroundColor ?: Green)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Spacer(modifier = Modifier.padding(16.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Padding(value = 8)
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .width(50.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = ui.headerLogo),
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = ui.headerTitle,
                                    fontWeight = FontWeight.Bold,
                                    color = ui.UIStyle?.titleColor ?: Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp)
                                )
                                Padding(value = 8)
                            }
                            Padding(value = 16)
                        }
                        Padding(value = 16)
                        Text(
                            text = ui.message,
                            textAlign = TextAlign.Justify,
                            color = ui.UIStyle?.messageTextColor ?: Color.Black,
                            modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp)
                        )
                        Padding(value = 8)
                        SimpleButton(
                            Button(
                                title = ui.actionBtnTitle,
                                onPressed = { dialogCloseAction.invoke() },
                                buttonStyle = ui.buttonStyle
                            )
                        )
                    }
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = ui.isCancelable,
                dismissOnClickOutside = ui.isCancelable
            ),
            shape = dialogShape
        )
    }
}
