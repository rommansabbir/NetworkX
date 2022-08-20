package com.rommansabbir.networkobserverexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rommansabbir.networkx.NetworkXProvider
import com.rommansabbir.networkx.dialog.NoInternetDialogV2
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class DummyActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)

        lifecycleScope.launchWhenCreated {
            try {
                NetworkXProvider.isInternetConnectedFlow.collect {
                    lifecycleScope.launch {
                        if (!it) {
                            if (!NoInternetDialogV2.isVisible) {
                                NoInternetDialogV2(
                                    activity = WeakReference(this@DummyActivity),
                                    title = "No Internet Bro",
                                    message = "This is just a dummy message",
                                    buttonTitle = "Okay",
                                    isCancelable = false
                                ) {
                                }
                            }
                        } else {
                            NoInternetDialogV2.forceClose()
                            Toast.makeText(
                                this@DummyActivity,
                                "Is connected? : $it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}