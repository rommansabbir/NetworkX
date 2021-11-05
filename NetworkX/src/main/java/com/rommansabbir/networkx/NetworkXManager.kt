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

internal class NetworkXManager constructor(
    private val application: Application,
    private val isSpeedMeterEnabled: Boolean
) {
    // Callback for activity lifecycle for this specific application
    private val activityCallback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityResumed(activity: Activity) {
            try {
                getConnectivityManager(application).apply {
                    registerNetworkCallback(
                        getNetworkRequest(),
                        getNetworkCallBack
                    )
                    if (isSpeedMeterEnabled) {
                        enabledSpeedMeter()
                        logThis("onActivityResumed: speed meter enabled")
                    }
                    logThis("onActivityResumed: listener registered")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                logThis(e.message)
            }
        }

        override fun onActivityPaused(activity: Activity) {
            try {
                getConnectivityManager(application).unregisterNetworkCallback(getNetworkCallBack)
                logThis("onActivityPaused: listener unregistered")
                if (isSpeedMeterEnabled) {
                    disableSpeedMeter()
                    logThis("onActivityResumed: speed meter disabled")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                logThis(e.message)
            }
        }

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

    }

    @Volatile
    private var trafficUtils: TrafficUtils? = null

    // Initialize the Manager
    init {
        try {
            getConnectivityManager(application)
            application.registerActivityLifecycleCallbacks(activityCallback)
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

    // Callback to get notified about network availability
    private val getNetworkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            NetworkXProvider.setConnection(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            NetworkXProvider.setConnection(false)
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