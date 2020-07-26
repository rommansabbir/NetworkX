package com.rommansabbir.networkobserverexample

import android.app.Application
import com.rommansabbir.networkx.NetworkX
import com.rommansabbir.networkx.NetworkXObservingStrategy

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * To Start observing network status using [NetworkX] call [NetworkX.startObserving] from application [onCreate] method
         * Otherwise when try to check if device is connected to the internet or not it will throw an [Exception]
         * Provide [Application] reference & [NetworkXObservingStrategy] to [NetworkX]
         * [NetworkXObservingStrategy] represent delay time in internet connection status checking
         * Sates are:
         * [NetworkXObservingStrategy.LOW]
         * [NetworkXObservingStrategy.HIGH]
         * [NetworkXObservingStrategy.MEDIUM]
         * [NetworkXObservingStrategy.REALTIME]
         */
        NetworkX.startObserving(this, NetworkXObservingStrategy.HIGH)

//        /**
//         * Also you can provide custom time interval in millis
//         * Custom State:
//         * [NetworkXObservingStrategy.CUSTOM]
//         */
//        NetworkX.startObserving(this, NetworkXObservingStrategy.CUSTOM(15 * 1000))
    }
}