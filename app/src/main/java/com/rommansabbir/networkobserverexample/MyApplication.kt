package com.rommansabbir.networkobserverexample

import android.app.Application
import com.rommansabbir.networkx.NetworkXProvider
import com.rommansabbir.networkx.core.NetworkX
import com.rommansabbir.networkx.core.NetworkXCore
import com.rommansabbir.networkx.strategy.NetworkXObservingStrategy

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        NetworkXProvider.init(this)

        /**
         * To Start observing network status using [NetworkX] call [NetworkXCore.init] from application [onCreate] method
         * Otherwise when try to check if device is connected to the internet or not it will throw an [Exception]
         * Provide [Application] reference & [NetworkXObservingStrategy] to [NetworkX]
         *
         * [NetworkXObservingStrategy] represent delay time in internet connection status checking
         * Sates are:
         * [NetworkXObservingStrategy.LOW]
         * [NetworkXObservingStrategy.HIGH]
         * [NetworkXObservingStrategy.MEDIUM]
         * [NetworkXObservingStrategy.REALTIME]
         */
//        NetworkXCore.init(this, NetworkXObservingStrategy.MEDIUM)

//        /**
//         * Also you can provide custom time interval in millis
//         * Custom State:
//         * [NetworkXObservingStrategy.CUSTOM]
//         */
//        NetworkX.init(this, NetworkXObservingStrategy.CUSTOM(15 * 1000))
    }
}