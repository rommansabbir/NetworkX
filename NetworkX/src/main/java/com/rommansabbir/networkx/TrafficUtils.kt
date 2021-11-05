package com.rommansabbir.networkx

import android.net.TrafficStats
import kotlinx.coroutines.*
import java.util.*

/**
 * To monitor current network speed.
 * All the work should be done by using [CoroutineScope].
 * So that all ongoing operations can be cancelled on demand.
 */
class TrafficUtils {
    private var ioScope: CoroutineScope? = null
    private var mainScope: CoroutineScope? = null

    // Initialize all scopes
    fun enabledScopes() {
        synchronized(Any()) {
            ioScope?.cancel()
            ioScope = CoroutineScope(Dispatchers.IO)
            mainScope?.cancel()
            mainScope = CoroutineScope(Dispatchers.Main)
        }
    }

    // Release all scopes
    fun releaseScopes() {
        synchronized(Any()) {
            ioScope?.cancel()
            ioScope = null
            mainScope?.cancel()
            mainScope = null
        }
    }

    private val gb: Long = 1000000000
    private val mb: Long = 1000000
    private val kb: Long = 1000

    /**
     * Get current network speed which return an instance of [LastKnownSpeed] instance using the callback.
     *
     * @param onUpdate, callback for [LastKnownSpeed] update
     */
    fun getNetworkSpeed(onUpdate: (lastKnownSpeed: LastKnownSpeed) -> Unit) {
        try {
            if (ioScope == null || mainScope == null) {
                releaseScopes()
                enabledScopes()
            }
            ioScope?.launch {
                val downloadSpeedOutput: String
                val units: String
                val mBytesPrevious = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
                delay(1000)
                val mBytesCurrent = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
                val mNetworkSpeed = mBytesCurrent - mBytesPrevious
                val mDownloadSpeedWithDecimals: Float
                val networkSpeedType: String
                when {
                    mNetworkSpeed >= gb -> {
                        mDownloadSpeedWithDecimals = mNetworkSpeed.toFloat() / gb.toFloat()
                        units = " ${NetworkSpeedType.GB}"
                        networkSpeedType = NetworkSpeedType.GB
                    }
                    mNetworkSpeed >= mb -> {
                        mDownloadSpeedWithDecimals = mNetworkSpeed.toFloat() / mb.toFloat()
                        units = " ${NetworkSpeedType.MB}"
                        networkSpeedType = NetworkSpeedType.MB
                    }
                    else -> {
                        mDownloadSpeedWithDecimals = mNetworkSpeed.toFloat() / kb.toFloat()
                        units = " ${NetworkSpeedType.KB}"
                        networkSpeedType = NetworkSpeedType.KB
                    }
                }
                downloadSpeedOutput =
                    if (units != " ${NetworkSpeedType.KB}" && mDownloadSpeedWithDecimals < 100) {
                        String.format(Locale.US, "%.1f", mDownloadSpeedWithDecimals)
                    } else {
                        mDownloadSpeedWithDecimals.toInt().toString()
                    }
                mainScope?.launch {
                    onUpdate.invoke(
                        LastKnownSpeed(
                            downloadSpeedOutput,
                            networkSpeedType,
                            downloadSpeedOutput + units
                        )
                    )
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}