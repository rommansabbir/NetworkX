package com.rommansabbir.networkx

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle

internal class NetworkXManager constructor(private val application: Application) {
    // Callback for activity lifecycle for this specific application
    private val activityCallback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityResumed(activity: Activity) {
            try {
                getConnectivityManager(application).registerNetworkCallback(
                    getNetworkRequest(),
                    getNetworkCallBack
                )
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }

        override fun onActivityPaused(activity: Activity) {
            try {
                getConnectivityManager(application).unregisterNetworkCallback(getNetworkCallBack)
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

    }

    // Initialize the Manager
    init {
        try {
            getConnectivityManager(application)
            application.registerActivityLifecycleCallbacks(activityCallback)
        }
        catch (e : Exception){
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