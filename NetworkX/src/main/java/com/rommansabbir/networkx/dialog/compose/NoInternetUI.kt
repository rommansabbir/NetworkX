package com.rommansabbir.networkx.dialog.compose

import androidx.annotation.DrawableRes

data class NoInternetUI(
    val headerTitle: String,
    @DrawableRes val headerLogo: Int,
    val message: String,
    val actionBtnTitle: String,
    val isCancelable: Boolean,
    val UIStyle: UIStyle? = null,
    val buttonStyle: ButtonStyle? = null
)