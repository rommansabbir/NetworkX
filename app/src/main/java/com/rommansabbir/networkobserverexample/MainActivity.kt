package com.rommansabbir.networkobserverexample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rommansabbir.networkx.NetworkXProvider
import com.rommansabbir.networkx.NetworkXProvider.isInternetConnectedFlow
import com.rommansabbir.networkx.NetworkXProvider.lastKnownSpeedFlow
import com.rommansabbir.networkx.dialog.NoInternetDialogV2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = NetworkXProvider.isInternetConnected
        textView.text = "Internet connection status: $status"

        //
        NetworkXProvider.isInternetConnectedLiveData.observe(this) {
            it?.let {
                NoInternetDialogV2.forceClose()
                if (!it) {
                    NoInternetDialogV2(
                        activity = WeakReference(this@MainActivity),
                        title = "No Internet Bro",
                        message = "This is just a dummy message",
                        buttonTitle = "Okay",
                        isCancelable = false
                    ) {
                    }
                }
            }
        }

/*        NetworkXProvider.lastKnownSpeed.let {
            textView2.text =
                "Last Known Speed: Speed - ${it.speed} | Type - ${it.networkTypeNetwork} | Simplified Speed - ${it.simplifiedSpeed}"
        }*/

        lifecycleScope.launchWhenCreated {
            try {
                lastKnownSpeedFlow.collect {
                    lifecycleScope.launch {
                        textView2.text =
                            "Last Known Speed: Speed - ${it.speed} | Type - ${it.networkTypeNetwork} | Simplified Speed - ${it.simplifiedSpeed}"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        lifecycleScope.launchWhenCreated {
            try {
                isInternetConnectedFlow.collect {
                    lifecycleScope.launch {
                        textView.text = "Internet connection status: $it"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        button.setOnClickListener {
            NoInternetDialogV2.forceClose()
            startActivity(Intent(this@MainActivity, DummyActivity::class.java))
        }
    }

}