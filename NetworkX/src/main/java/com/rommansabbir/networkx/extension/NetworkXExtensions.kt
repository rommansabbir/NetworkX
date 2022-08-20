package com.rommansabbir.networkx.extension

import androidx.lifecycle.LiveData
import com.rommansabbir.networkx.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Return if internet is connected or not.
 */
val isInternetConnected: Boolean = NetworkXProvider.isInternetConnected

/**
 * Return if internet is connected or not as [LiveData].
 */
val isInternetConnectedLiveData: LiveData<Boolean> = NetworkXProvider.isInternetConnectedLiveData

/**
 * Return if internet is connected or not as [MutableStateFlow].
 */
val isInternetConnectedFlow: MutableStateFlow<Boolean> = NetworkXProvider.isInternetConnectedFlow

/**
 * Return [LastKnownSpeed].
 */
val lastKnownSpeed: LastKnownSpeed = NetworkXProvider.lastKnownSpeed

/**
 * Return [LastKnownSpeed] as [LiveData]
 */
val lastKnownSpeedLiveData: LiveData<LastKnownSpeed> = NetworkXProvider.lastKnownSpeedLiveData

/**
 * Return [LastKnownSpeed] as [MutableStateFlow].
 */
val lastKnownSpeedFlow: MutableStateFlow<LastKnownSpeed> = NetworkXProvider.lastKnownSpeedFlow

/**
 * Return a default lazy instance of  [LastKnownSpeed].
 */
internal val getDefaultLastKnownSpeed by lazy {
    LastKnownSpeed(NetworkSpeedType.NONE, NetworkSpeedType.NONE, NetworkSpeedType.NONE)
}

/**
 * Return a default lazy instance of [CoroutineScope] which works under [Dispatchers.IO].
 */
internal val getDefaultIOScope by lazy { CoroutineScope(Dispatchers.IO) }

/**
 * Initialize [NetworkXProvider].
 *
 * @param config [NetworkXConfig].
 */

@Deprecated(
    "Use new extension API", replaceWith = ReplaceWith(
        "smartEnableNetworkX(SmartConfig(application, true, NetworkXLifecycle.Application))",
        imports = arrayOf(
            "com.rommansabbir.networkx.extension.smartEnableNetworkX",
            "com.rommansabbir.networkx.SmartConfig",
            "com.rommansabbir.networkx.NetworkXLifecycle"
        )
    )
)
fun enableNetworkX(config: NetworkXConfig) = NetworkXProvider.enable(config)

/**
 * Initialize [NetworkXProvider].
 *
 * @param config [SmartConfig].
 */
fun smartEnableNetworkX(config: SmartConfig) = NetworkXProvider.enable(config)
