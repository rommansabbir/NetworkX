package com.rommansabbir.networkx.dialog.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rommansabbir.networkx.ui.theme.Green

@Composable
fun SimpleButton(state: Button) {
    val modifier = Modifier.size(width = 50.dp, height = 50.dp)

    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .background(brush = SolidColor(state.buttonStyle?.backgroundColor ?: Green))
        .clickable(enabled = true, onClick = {
            state.onPressed.invoke()
        }, role = Role.Button)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = rowModifier
    ) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            state.leftIcon?.let {
                IconButton(onClick = {}) {
                    Icon(painterResource(id = it), contentDescription = "")
                }
            } ?: run { EmptySquareButton() }
        }
        Padding(value = 8)
        Text(
            text = state.title,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = state.buttonStyle?.textColor ?: Color.White,
            fontWeight = FontWeight.Bold
        )
        Padding(value = 8)
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            state.rightIcon?.let {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "")
                }
            } ?: run { EmptySquareButton() }
        }
    }
}

