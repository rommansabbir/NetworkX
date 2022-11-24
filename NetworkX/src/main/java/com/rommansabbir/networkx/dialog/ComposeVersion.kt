package com.rommansabbir.networkx.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.rommansabbir.networkx.R
import com.rommansabbir.networkx.ui.theme.Green

@Composable
fun APSingleActionDialog(
    dialogState: Boolean = false,
    title: String = "Please try again later...",
    description: String = "",
    actionTitle: String = "Okay",
    isCancelable: Boolean = true,
    onCancelled: () -> Unit = {},
    onButtonPressed: () -> Unit = {},
) {
    val iconModifier = Modifier.size(width = 50.dp, height = 50.dp)
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
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Green)
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Spacer(modifier = Modifier.padding(16.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .width(50.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_baseline_cloud_off_24),
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.no_internet),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                            Spacer(modifier = Modifier.padding(16.dp))
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = title,
                            textAlign = TextAlign.Justify,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        APSimpleButton(title = actionTitle,
                            onPressed = { dialogCloseAction.invoke() })
                    }
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = isCancelable,
                dismissOnClickOutside = isCancelable
            ),
            shape = dialogShape
        )
    }
}

@Composable
fun APSimpleButton(
    title: String,
    onPressed: () -> Unit,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
) {
    //Fixed size for icons
    val modifier = Modifier.size(width = 50.dp, height = 50.dp)


    // Callback to notify client about button pressed action
    // @{isLocked} must be false for @{onPressed} to be invoked
    val internalCallback: () -> Unit = {
        onPressed.invoke()
    }

    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .background(brush = SolidColor(Green))
        .clickable(enabled = true, onClick = {
            internalCallback.invoke()
        }, role = Role.Button)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = rowModifier
    ) {

        //Box to define the left icon
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            leftIcon?.let {
                IconButton(onClick = {}) {
                    Icon(painterResource(id = it), contentDescription = "")
                }
            } ?: run { emptySquareButton() }
        }

        //Padding
        Spacer(modifier = Modifier.width(8.dp))

        //Title for the button
        Text(
            text = title,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            /*fontFamily = APFontBold*/
        )

        //Padding
        Spacer(modifier = Modifier.width(8.dp))

        //Box to define the right icon
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            rightIcon?.let {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "")
                }
            } ?: run { emptySquareButton() }
        }
    }
}

// Empty square button
@Composable
fun emptySquareButton(title: String = "") {
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