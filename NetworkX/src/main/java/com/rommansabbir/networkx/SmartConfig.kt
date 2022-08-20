package com.rommansabbir.networkx

import android.app.Application

data class SmartConfig(
    val application: Application,
    val enableSpeedMeter: Boolean,
    val lifecycle: NetworkXLifecycle
)