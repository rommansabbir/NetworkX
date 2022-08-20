package com.rommansabbir.networkx

/**
 * Define the lifecycle for NetworkX. Either activity or application.
 */
sealed class NetworkXLifecycle {
    object Application : NetworkXLifecycle()
    object Activity : NetworkXLifecycle()
}
