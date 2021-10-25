package com.rommansabbir.networkx.strategy

/**
 * This sealed class represent delay time state for networking status checking purpose
 */
@Deprecated("Deprecated. Use NetworkXProvider instead")
sealed class NetworkXObservingStrategy {
    object LOW : NetworkXObservingStrategy()
    object MEDIUM : NetworkXObservingStrategy()
    object HIGH : NetworkXObservingStrategy()
    object REALTIME : NetworkXObservingStrategy()
    class CUSTOM(val mInterval: Long) : NetworkXObservingStrategy()
}