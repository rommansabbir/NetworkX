package com.rommansabbir.networkobserverexample

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rommansabbir.networkx.core.NetworkXCore
import com.rommansabbir.networkx.dialog.NoInternetDialog
import com.rommansabbir.networkx.strategy.NetworkXObservingStrategy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button_cancel).setOnClickListener {
            NetworkXCore.getNetworkX().cancelObservation()
            showMessage("Canceled")
        }
        findViewById<Button>(R.id.button_restart).setOnClickListener {
            NetworkXCore.getNetworkX().restartObservation()
            showMessage("Restarted")
        }
        findViewById<Button>(R.id.button_change).setOnClickListener {
            NetworkXCore.getNetworkX().updateStrategy(NetworkXObservingStrategy.HIGH)
            showMessage("Strategy Updated")
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun isCanceled() {
        showMessage("Is Canceled: ${NetworkXCore.getNetworkX().isCanceled()}")
    }

    override fun onResume() {
        super.onResume()
        NetworkXCore.getNetworkX().isInternetConnected()
        isCanceled()

        NetworkXCore.getNetworkX().isInternetConnectedLiveData().observe(
            this
        ) {
            it?.let {
                if (!it) {
                    if (!NoInternetDialog.isDialogVisible()) {
                        NoInternetDialog
                            .Companion
                            .Builder()
                            .withActivity(this)
                            .withTitle("No internet!")
                            .withMessage("Your device is not connected to the internet!")
                            .withActionCallback {
                                // User clicked `Retry` button
                            }
                            .build()
                            .show()
                    }
                }
            }
        }
    }
}