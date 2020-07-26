package com.rommansabbir.networkx

/**
 * This sealed class represent delay time state for networking status checking purpose
 */
sealed class NetworkXObservingStrategy {
    object LOW : NetworkXObservingStrategy()
    object MEDIUM : NetworkXObservingStrategy()
    object HIGH : NetworkXObservingStrategy()
    object REALTIME : NetworkXObservingStrategy()
    class CUSTOM(var mInterval: Long) : NetworkXObservingStrategy()
}