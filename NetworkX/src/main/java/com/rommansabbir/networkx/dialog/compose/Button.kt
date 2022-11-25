package com.rommansabbir.networkx.dialog.compose

data class Button(
    val title: String,
    val onPressed: () -> Unit,
    val leftIcon: Int? = null,
    val rightIcon: Int? = null,
    val buttonStyle: ButtonStyle? = null
)
