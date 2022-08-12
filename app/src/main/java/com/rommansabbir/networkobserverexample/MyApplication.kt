package com.rommansabbir.networkobserverexample

import android.app.Application
import com.rommansabbir.networkx.NetworkXConfig
import com.rommansabbir.networkx.NetworkXProvider

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        /**
         * Initialize NetworkX
         */
        val builder = NetworkXConfig
            .Builder()
            .withApplication(this)
            .withEnableSpeedMeter(true)
            .build()
        NetworkXProvider.enable(builder)
    }
}