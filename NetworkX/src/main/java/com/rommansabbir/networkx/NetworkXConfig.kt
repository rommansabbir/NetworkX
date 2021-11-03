package com.rommansabbir.networkx

import android.app.Application
import com.rommansabbir.networkx.NetworkXConfig.Builder
import com.rommansabbir.networkx.exceptions.NetworkXNotInitializedException

/**
 * This class represent the configurations for NetworkX.
 *
 * Use the [Builder] class to initialize [NetworkXConfig].
 *
 * @param application, [Application] reference to initialize NetworkX properly
 * @param enableSpeedMeter, determine if to enable monitoring network speed
 */
class NetworkXConfig private constructor(
    val application: Application,
    val enableSpeedMeter: Boolean
) {
    // Use the builder class to initialize NetworkXConfig
    class Builder {
        // States
        private lateinit var application: Application
        private var isSpeedMeterEnabled: Boolean = false

        // Hold application reference
        fun withApplication(application: Application): Builder {
            this.application = application
            return this
        }

        // Hold enable speed meter status reference
        fun withEnableSpeedMeter(value: Boolean): Builder {
            this.isSpeedMeterEnabled = value
            return this
        }

        // Return an new instance of NetworkXConfig
        fun build(): NetworkXConfig {
            if (!this::application.isInitialized) {
                throw NetworkXNotInitializedException()
            }
            return NetworkXConfig(application, isSpeedMeterEnabled)
        }
    }
}