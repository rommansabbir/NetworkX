package com.rommansabbir.networkx.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rommansabbir.networkx.exceptions.NetworkXUpdateStrategyException
import com.rommansabbir.networkx.strategy.NetworkXObservingStrategy
import kotlinx.coroutines.*

class NetworkXImpl(
    private val application: Application,
    private val initStrategy: NetworkXObservingStrategy
) : NetworkX {
    private var ioScope: CoroutineScope? = null
    private var mainScope: CoroutineScope? = null

    private var strategy: NetworkXObservingStrategy? = null

    private val STRATEGY_LOW: Long = 15 * 1000
    private val STRATEGY_MEDIUM: Long = 10 * 1000
    private val STRATEGY_HIGH: Long = 3 * 1000
    private val STRATEGY_REALTIME: Long = 1 * 1000

    private fun initScopes() {
        ioScope = CoroutineScope(Dispatchers.IO + Job())
        mainScope = CoroutineScope(Dispatchers.Main + Job())
    }

    private fun releaseScopes() {
        ioScope?.cancel()
        mainScope?.cancel()
        ioScope = null
        mainScope = null
    }

    init {
        this.strategy = initStrategy
        initScopes()
        if (strategy != null && ioScope != null && mainScope != null) {
            ioScope?.launch {
                startObserving(strategy!!)
            }
        }
    }

    private var isInternetConnected: Boolean = false
    private var isInternetConnectedLiveData: MutableLiveData<Boolean> =
        MutableLiveData()

    override fun isInternetConnected(): Boolean = isInternetConnected

    override fun isInternetConnectedLiveData(): LiveData<Boolean> =
        isInternetConnectedLiveData

    override fun cancelObservation() {
        releaseScopes()
    }

    override fun restartObservation() {
        if (ioScope != null && mainScope != null) {
            releaseScopes()
        }
        initScopes()
        if (strategy != null && ioScope != null && mainScope != null) {
            ioScope?.launch {
                startObserving(strategy!!)
            }
        }
    }

    @Throws(Exception::class)
    override fun updateStrategy(strategy: NetworkXObservingStrategy) {
        if (this.ioScope == null && this.mainScope == null) {
            throw NetworkXUpdateStrategyException()
        } else {
            this.strategy = strategy
        }
    }

    /**
     * Start observing network status by calling [isNetworkAvailable] method
     * which return the internet connection status.
     *
     * Update status to global accessible variables [isInternetConnected] and [isInternetConnectedLiveData]
     * Check for delay strategy
     * Set a delay according to the [NetworkXObservingStrategy] state.
     *
     * Developer can provide custom delay to this observation
     * Use recursive strategy to perform the same procedure again and again
     */
    suspend fun startObserving(strategy: NetworkXObservingStrategy) {
        if (ioScope != null && mainScope != null) {
            val temp = isNetworkAvailable(application)
            mainScope?.launch {
                isInternetConnected = temp
                isInternetConnectedLiveData.value = temp
            }
            when (strategy) {
                is NetworkXObservingStrategy.LOW -> delay(STRATEGY_LOW)
                is NetworkXObservingStrategy.MEDIUM -> delay(STRATEGY_MEDIUM)
                is NetworkXObservingStrategy.HIGH -> delay(STRATEGY_HIGH)
                is NetworkXObservingStrategy.REALTIME -> delay(STRATEGY_REALTIME)
                is NetworkXObservingStrategy.CUSTOM -> delay(strategy.mInterval)
            }
            this.strategy?.let {
                startObserving(it)
            }
        }
    }

    /**
     * This method is responsible to check network status.
     * Check for build version.
     * If device is connected to the internet.
     *
     * @param context
     *
     * @return [Boolean]
     */
    private fun isNetworkAvailable(
        context: Context
    ): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    ) -> return true
                    capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ) -> return true
                    capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_ETHERNET
                    ) -> return true
                }
            }
        } else {
            return connectivityManager.activeNetworkInfo != null &&
                    connectivityManager.activeNetworkInfo!!.isConnected
        }
        return false
    }
}