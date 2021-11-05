package com.rommansabbir.networkx

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkXProvider {
    /**
     * Store the [NetworkXManager] instance to update client about the internet connectivity status
     */
    @Volatile
    private var manager: NetworkXManager? = null

    /**
     * Hold the connected or not state, by default its set to false
     */
    private var connected: Boolean = false

    /**
     * Update the current internet connections status to [NetworkXProvider] to be consumed by the client.
     * This method is for internal usages only.
     *
     * @param value, new connection status
     */
    internal fun setConnection(value: Boolean) {
        synchronized(value) {
            Handler(Looper.getMainLooper()).post {
                connected = value
                isInternetConnectedMutableLiveData.value = value
            }
        }
    }

    /**
     * Public access point to get the current internet connection status
     */
    val isInternetConnected: Boolean
        get() = connected

    /**
     * Public access point to get the current internet connection status which can be observed from Activity/Fragment.
     */
    private var isInternetConnectedMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val isInternetConnectedLiveData: LiveData<Boolean>
        get() = isInternetConnectedMutableLiveData


    // Store last known speed
    private var lastKnownSpeedRef: LastKnownSpeed? = null

    /**
     * Public access point to get the last known network speed
     */
    val lastKnownSpeed: LastKnownSpeed?
        get() = lastKnownSpeedRef

    private val lastKnownSpeedLiveDataRef: MutableLiveData<LastKnownSpeed> = MutableLiveData(null)

    /**
     * Public access point to get the last known network speed which can be observed from Activity/Fragment.
     */
    val lastKnownSpeedLiveData: LiveData<LastKnownSpeed>
        get() = lastKnownSpeedLiveDataRef

    fun setNetworkSpeed(value: LastKnownSpeed) {
        synchronized(value) {
            lastKnownSpeedRef = value
            lastKnownSpeedLiveDataRef.value = value
        }
    }

    /**
     * Main entry point for the client.
     * First check for [NetworkXManager] instance, if the status is null then initialize it properly
     * else ignore the initialization.
     *
     * @param application, [Application] reference
     */
    @Deprecated("Use NetworkXProvider.enable(config: NetworkXConfig) to initialize NetworkX properly")
    fun init(application: Application, enableSpeedMeter: Boolean = false) {
        try {
            if (manager != null) {
                return
            }
            manager = NetworkXManager(application, enableSpeedMeter)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Main entry point for the client.
     * First check for [NetworkXManager] instance, if the status is null then initialize it properly
     * else ignore the initialization.
     *
     * @param config, [NetworkXConfig] reference
     */
    fun enable(config: NetworkXConfig) {
        try {
            if (manager != null) {
                return
            }
            manager = NetworkXManager(config.application, config.enableSpeedMeter)
        } catch (e: Exception) {
            throw e
        }
    }
}