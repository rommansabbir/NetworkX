package com.rommansabbir.networkx.core

import android.app.Application
import com.rommansabbir.networkx.exceptions.NetworkXNotInitializedException
import com.rommansabbir.networkx.strategy.NetworkXObservingStrategy

@Deprecated("Deprecated. Use NetworkXProvider instead")
class NetworkXCore private constructor(private val context: Application) {
    companion object {

        private var networkX: NetworkX? = null

        @Deprecated("Deprecated. Use NetworkXProvider instead")
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

        @Deprecated("Deprecated. Use NetworkXProvider instead")
        fun init(context: Application, strategy: NetworkXObservingStrategy) {
            networkX = NetworkXImpl(context, strategy)
        }
    }
}