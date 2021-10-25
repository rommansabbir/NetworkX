package com.rommansabbir.networkx.core

import androidx.lifecycle.LiveData
import com.rommansabbir.networkx.strategy.NetworkXObservingStrategy

@Deprecated("Deprecated. Use NetworkXProvider instead")
interface NetworkX {
    /**
     * To check if device is connected to the internet or not
     *
     * @return [Boolean]
     */
    @Deprecated("Deprecated. Use NetworkXProvider instead")
    fun isInternetConnected(): Boolean

    /**
     * To observe device internet connection status, if connected or not.
     *
     * @return [LiveData<Boolean>]
     */
    @Deprecated("Deprecated. Use NetworkXProvider instead")
    fun isInternetConnectedLiveData(): LiveData<Boolean>

    /**
     * Cancel internet observation
     */
    @Deprecated("Deprecated. Use NetworkXProvider instead")
    fun cancelObservation()

    /**
     * Restart internet observation
     */
    @Deprecated("Deprecated. Use NetworkXProvider instead")
    fun restartObservation()

    /**
     * Update internet observation strategy to the given strategy
     *
     * @param strategy, [NetworkXObservingStrategy]
     */
    @Deprecated("Deprecated. Use NetworkXProvider instead")
    fun updateStrategy(strategy: NetworkXObservingStrategy)

    /**
     * Check if the observation is canceled or not
     */
    @Deprecated("Deprecated. Use NetworkXProvider instead")
    fun isCanceled(): Boolean
}