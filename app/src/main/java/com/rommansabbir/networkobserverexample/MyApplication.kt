package com.rommansabbir.networkobserverexample

import android.app.Application
import com.rommansabbir.networkx.NetworkXLifecycle
import com.rommansabbir.networkx.NetworkXProvider
import com.rommansabbir.networkx.SmartConfig

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        /*Deprecated way*/
        /*val builder = NetworkXConfig
            .Builder()
            .withApplication(this)
            .withEnableSpeedMeter(true)
            .build()
        NetworkXProvider.enable(builder)*/

        /*New way*/
        NetworkXProvider.enable(SmartConfig(this, true, NetworkXLifecycle.Application))

        /*Smart extension*/
        /*smartEnableNetworkX(SmartConfig(this, true, NetworkXLifecycle.Application))*/
    }
}