package com.rommansabbir.networkx.core

import androidx.lifecycle.LiveData
import com.rommansabbir.networkx.strategy.NetworkXObservingStrategy

interface NetworkX {
    /**
     * To check if device is connected to the internet or not
     *
     * @return [Boolean]
     */
    fun isInternetConnected(): Boolean

    /**
     * To observe device internet connection status, if connected or not.
     *
     * @return [LiveData<Boolean>]
     */
    fun isInternetConnectedLiveData(): LiveData<Boolean>

    /**
     * Cancel internet observation
     */
    fun cancelObservation()

    /**
     * Restart internet observation
     */
    fun restartObservation()

    /**
     * Update internet observation strategy to the given strategy
     *
     * @param strategy, [NetworkXObservingStrategy]
     */
    fun updateStrategy(strategy: NetworkXObservingStrategy)
}