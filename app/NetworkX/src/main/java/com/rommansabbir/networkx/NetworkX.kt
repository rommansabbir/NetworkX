package com.rommansabbir.networkx

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/*
Copyright (C) 2020 Romman Sabbir

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

class NetworkX private constructor() {
    companion object {
        /**
         * Required message's
         */
        private const val ERROR =
            "Did you called NetworkX.startObserving() from onCreate() in your application class?"
        private const val CANCEL_JOB = "Observing network status job has been canceled"

        /**
         * Interval time in millis
         */
        private const val STRATEGY_LOW: Long = 15 * 1000
        private const val STRATEGY_MEDIUM: Long = 10 * 1000
        private const val STRATEGY_HIGH: Long = 3 * 1000
        private const val STRATEGY_REALTIME: Long = 1 * 1000

        /**
         * Application instance to check if [startObserving] if called from [Application] class or not
         * Otherwise will throw an [Exception]
         */
        private var mAppContext: Application? = null

        /**
         * Wrap internet connected or not status into a [MutableLiveData] so that developer can observe
         * status in required fragment or activity
         */
        private var mNetworkStatusLiveData = MutableLiveData<Boolean>()

        /**
         * To store internet is connected or not status
         */
        private var mNetworkStatus: Boolean = true

        /**
         * [CoroutineScope] to observe network status in a [Dispatchers.IO] thread
         */
        private var mCoroutineScope: CoroutineContext? = null

        /**
         * To get status of internet is connected or not
         *
         * @return [Boolean]
         */
        fun isConnected(): Boolean {
            return if (mAppContext == null) {
                throw Exception(ERROR)
            } else {
                mNetworkStatus
            }
        }

        /**
         * To get status of internet is connected or not wrap into [MutableLiveData]
         * so that I can be observed from activity or fragment
         *
         * @return [MutableLiveData]
         */

        fun isConnectedLiveData(): MutableLiveData<Boolean> {
            return if (mAppContext == null) {
                throw Exception(ERROR)
            } else {
                mNetworkStatusLiveData
            }
        }

        /**
         * Start observing internet connected or not
         * Create instance of [NetworkX]
         * Store [Application] as [context] instance
         * Provide delay time strategy by providing [NetworkXObservingStrategy] state
         * Execute all required executing under a [CoroutineDispatcher]
         *
         * @param context, [Application]
         * @param strategy, [NetworkXObservingStrategy]
         */
        fun startObserving(context: Application, strategy: NetworkXObservingStrategy) {
            val mInstance = NetworkX()
            this.mAppContext = context
            this.mCoroutineScope = Dispatchers.IO + Job()
            this.mCoroutineScope?.let {
                CoroutineScope(it).launch {
                    mInstance.startObserving(context, strategy)
                }
            }
        }

        /**
         * Cancel ongoing network observing
         * If [mAppContext] or [mCoroutineScope] is null
         * Throw [Exception]
         */
        fun cancelObserving() {
            if (mAppContext == null || mCoroutineScope == null) {
                throw Exception(ERROR)
            } else {
                mCoroutineScope?.cancel(CancellationException(CANCEL_JOB))
                mCoroutineScope = null
            }
        }
    }

    /**
     * Start observing network status by calling [isNetworkAvailable] method
     * which return the internet connection status
     * Update status to global accessible variables [mNetworkStatus] and [mNetworkStatusLiveData]
     * Check for delay strategy
     * Set a delay according to the [NetworkXObservingStrategy] state
     * Developer can provide custom delay to this observation
     * Use recursive strategy to perform the same procedure again and again
     *
     * @param mContext, [Application]
     * @param mStrategy, [NetworkXObservingStrategy]
     */
    suspend fun startObserving(mContext: Application, mStrategy: NetworkXObservingStrategy) {
        val mTempValue = isNetworkAvailable(mContext)
        mNetworkStatusLiveData.postValue(mTempValue)
        mNetworkStatus = mTempValue
        when (mStrategy) {
            is NetworkXObservingStrategy.LOW -> delay(STRATEGY_LOW)
            is NetworkXObservingStrategy.MEDIUM -> delay(STRATEGY_MEDIUM)
            is NetworkXObservingStrategy.HIGH -> delay(STRATEGY_HIGH)
            is NetworkXObservingStrategy.REALTIME -> delay(STRATEGY_REALTIME)
            is NetworkXObservingStrategy.CUSTOM -> delay(mStrategy.mInterval)
        }
        startObserving(mContext, mStrategy)
    }

    /**
     * This method is responsible to check network status
     * Check for build version
     * If device is connected to the internet return [true] else [false]
     *
     * @param context
     *
     * @return [Boolean]
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                }
            }
        } else {
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
        }
        return false
    }
}