package com.rommansabbir.networkx

/**
 * manage repeat count
 */
enum class RepeatStrategy {
    INFINITY,     // repeat infinity
    ONCE,     // repeat just once
    CONNECTION,     // repeat till first connection
    DISCONNECTION,     // repeat till first disconnection
}