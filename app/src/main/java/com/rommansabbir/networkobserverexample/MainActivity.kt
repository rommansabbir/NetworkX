package com.rommansabbir.networkobserverexample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rommansabbir.networkx.NetworkX

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()

//        /**
//         * To get status of internet connection for single time, use [NetworkX.isConnected] method to retrieve connection status
//         */
//        NetworkX.isConnected().let {
//            when (it) {
//                true -> {
//                    /**
//                     * Do your stuff here when internet is connected
//                     */
//                    Log.d("NetworkX", "Is internet connected: $it")
//                }
//                else -> {
//                    /**
//                     * Do your stuff here when internet is not connected
//                     */
//                    Log.d("NetworkX", "Is internet connected: $it")
//                }
//            }
//        }
//
//        /**
//         * Or you can use extension function
//         */
//        isInternetConnected().let {
//            when (it) {
//                true -> {
//                    /**
//                     * Do your stuff here when internet is connected
//                     */
//                    Log.d("NetworkX", "Is internet connected: $it")
//                }
//                else -> {
//                    /**
//                     * Do your stuff here when internet is not connected
//                     */
//                    Log.d("NetworkX", "Is internet connected: $it")
//                }
//            }
//        }

        /**
         * To get status of internet connection in realtime, use [NetworkX.isConnectedLiveData] method to retrieve connection status
         */
        NetworkX.isConnectedLiveData().observe(
            this,
            Observer {
                when (it) {
                    true -> {
                        /**
                         * Do your stuff here when internet is connected
                         */
                        Log.d("NetworkX", "Is internet connected: $it")
                        Toast.makeText(this, "Internet connected", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        /**
                         * Do your stuff here when internet is not connected
                         */
                        Log.d("NetworkX", "Is internet connected: $it")
                        Toast.makeText(this, "Internet not connected", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

//        /**
//         * Or you can use extension function
//         */
//        isInternetConnectedLiveData().observe(this, Observer {
//            when (it) {
//                true -> {
//                    /**
//                     * Do your stuff here when internet is connected
//                     */
//                    Log.d("NetworkX", "Is internet connected: $it")
//                }
//                else -> {
//                    /**
//                     * Do your stuff here when internet is not connected
//                     */
//                    Log.d("NetworkX", "Is internet connected: $it")
//                }
//            }
//        })
    }

    override fun onStop() {
//        /**
//         * If you want to cancel the observing internet is connected status, use [NetworkX.cancelObserving] method
//         */
//        NetworkX.cancelObserving()
//
//        /**
//         * Or you can use extension function
//         */
//        cancelObservingIsInternetConnected()

        super.onStop()
    }
}