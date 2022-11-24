package com.rommansabbir.networkx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rommansabbir.networkx.dialog.APSingleActionDialog
import com.rommansabbir.networkx.ui.theme.NetworkObserverExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetworkObserverExampleTheme {
                // A surface container using the 'background' color from the theme
                Entry()
            }
        }
    }
}

@Composable
fun Entry() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        APSingleActionDialog(true)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NetworkObserverExampleTheme {
        Entry()
    }
}