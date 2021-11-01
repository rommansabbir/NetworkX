package com.rommansabbir.networkx

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle

internal class NetworkXManager constructor(private val application: Application) {
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
                    when (getConnectionType(activity)) {
                        0 -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            }
                            else{
//                                getNetworkCapabilities(activeNetwork)
                            }
                        }
                        1 -> {

                        }
                        2 -> {

                        }
                        3 -> {

                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onActivityPaused(activity: Activity) {
            try {
                getConnectivityManager(application).unregisterNetworkCallback(getNetworkCallBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

    }

    fun getConnectionType(context: Context): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            result = 2
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            result = 1
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                            result = 3
                        }
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    when (type) {
                        ConnectivityManager.TYPE_WIFI -> {
                            result = 2
                        }
                        ConnectivityManager.TYPE_MOBILE -> {
                            result = 1
                        }
                        ConnectivityManager.TYPE_VPN -> {
                            result = 3
                        }
                    }
                }
            }
        }
        return result
    }

    // Initialize the Manager
    init {
        try {
            getConnectivityManager(application)
            application.registerActivityLifecycleCallbacks(activityCallback)
        } catch (e: Exception) {
            e.printStackTrace()
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
}