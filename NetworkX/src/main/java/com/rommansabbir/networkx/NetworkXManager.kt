package com.rommansabbir.networkx

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.net.InetAddress

internal class NetworkXManager constructor(
    private val smartConfig: SmartConfig
) {
    // Callback for activity lifecycle for this specific application
    private val activityCallback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            when (smartConfig.lifecycle) {
                NetworkXLifecycle.Activity -> {
                    initObservation()
                }
                NetworkXLifecycle.Application -> {
                    if (!isObservationRunning) {
                        initObservation()
                    }
                }
            }
        }

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {
            when (smartConfig.lifecycle) {
                NetworkXLifecycle.Activity -> {
                    stopObservation()
                }
                NetworkXLifecycle.Application -> {
                    logThis("stopObservation(): can't stop observation since the lifecycle is set to Application")
                }
            }
        }

    }

    private fun stopObservation() {
        isObservationRunning = try {
            getConnectivityManager(smartConfig.application).unregisterNetworkCallback(
                getNetworkCallBack
            )
            logThis("stopObservation(): listener unregistered")
            if (smartConfig.enableSpeedMeter) {
                disableSpeedMeter()
                logThis("stopObservation(): speed meter disabled")
            }
            false
        } catch (e: Exception) {
            e.printStackTrace()
            logThis(e.message)
            false
        }
    }

    @Volatile
    private var isObservationRunning: Boolean = false

    private fun initObservation() {
        try {
            getConnectivityManager(smartConfig.application).apply {
                registerNetworkCallback(
                    getNetworkRequest(),
                    getNetworkCallBack
                )
                if (smartConfig.enableSpeedMeter) {
                    enabledSpeedMeter()
                    logThis("initObservation(): speed meter enabled")
                }
                logThis("initObservation(): listener registered")
                isObservationRunning = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logThis(e.message)
            isObservationRunning = false
        }
    }

    @Volatile
    private var trafficUtils: TrafficUtils? = null

    // Initialize the Manager
    init {
        try {
            getConnectivityManager(smartConfig.application)
            smartConfig.application.registerActivityLifecycleCallbacks(activityCallback)
            trafficUtils = TrafficUtils()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val mInterval = 1000
    private var mHandler: Handler? = null

    //Monitor network speed in a loop
    private val runnableCode: Runnable = object : Runnable {
        override fun run() {
            try {
                trafficUtils?.getNetworkSpeed {
                    NetworkXProvider.setNetworkSpeed(it)
                    logThis("Internet speed: $it")
                } ?: run {
                    logThis("Traffic Utils instance is null")
                }
                mHandler?.postDelayed(this, mInterval.toLong())
            } catch (e: Exception) {
                e.printStackTrace()
                logThis(e.message)
            }
        }
    }


    // Get ConnectivityManager which is a system service
    private fun getConnectivityManager(application: Application) =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    // Provide the network request
    private fun getNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            .build()
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            return !InetAddress.getByName("google.com").equals("")
        } catch (e: java.lang.Exception) {
            false
        }
    }

    private fun updateInternetConnectionStatus() {
        try {
            NetworkXProvider.setConnection(isInternetAvailable())
        } catch (e: Exception) {
            NetworkXProvider.setConnection(false)
        }
    }

    // Callback to get notified about network availability
    private val getNetworkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            updateInternetConnectionStatus()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            updateInternetConnectionStatus()
        }
    }

    // Disable current network speed monitoring
    private fun disableSpeedMeter() {
        try {
            trafficUtils?.releaseScopes()
            if (mHandler != null) {
                mHandler!!.removeCallbacks(runnableCode)
                mHandler = null
                logThis("Callback removed, Handler instance is null")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logThis(e.message)
        }
    }

    // Enable current network speed monitoring
    private fun enabledSpeedMeter() {
        try {
            trafficUtils?.enabledScopes()
            if (mHandler != null) {
                mHandler!!.removeCallbacks(runnableCode)
                mHandler = null
                logThis("Callback removed, Handler instance is null")
            }
            mHandler = Handler(Looper.getMainLooper())
            mHandler!!.post(runnableCode)
            logThis("OnNetwork Update - Callback added with Handler new instance")
        } catch (e: Exception) {
            e.printStackTrace()
            logThis(e.message)
        }
    }

    private fun logThis(log: String?) {
        Log.d("NetworkX:", log ?: "Something went wrong")
    }
}