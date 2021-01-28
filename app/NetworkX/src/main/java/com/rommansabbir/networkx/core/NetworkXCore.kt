package com.rommansabbir.networkx.core

import android.app.Application
import com.rommansabbir.networkx.exceptions.NetworkXNotInitializedException
import com.rommansabbir.networkx.strategy.NetworkXObservingStrategy

class NetworkXCore private constructor(private val context: Application) {
    companion object {

        private var networkX: NetworkX? = null

        @Throws(Exception::class)
        fun getNetworkX(): NetworkX {
            when (networkX == null) {
                true -> {
                    throw NetworkXNotInitializedException()
                }
                else -> {
                    return networkX!!
                }
            }
        }

        fun init(context: Application, strategy: NetworkXObservingStrategy) {
            networkX = NetworkXImpl(context, strategy)
        }
    }
}