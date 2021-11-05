[![Release](https://jitpack.io/v/jitpack/android-example.svg)](https://jitpack.io/#rommansabbir/NetworkX)

# NetworkX

An easy & handy library to monitor device internet connection status.

---

## Documentation

### Installation

Step 1. Add the JitPack repository to your build file .

```gradle
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency.

```gradle
    dependencies {
            implementation 'com.github.rommansabbir:NetworkX:Tag'
    }
```

---

### Version available

| Releases
| ------------- |
| 3.3.0         |


# Usages

## Step 1:
Initialize `NetworkX` from your `Application.onCreate()`
````
    val builder = NetworkXConfig.Builder()
        .withApplication(this)
        // You can disable speed meter if not required
        .withEnableSpeedMeter(true)
        .build()
    NetworkXProvider.enable(builder)
````

## Step 2:
- To check internet connection status, simply call `NetworkXProvider.isInternetConnected` which return a `Boolean` value.
````
    val status = NetworkXProvider.isInternetConnected
    textView.text = "Internet connection status: $status"
````

- To observe the internet connection status, start observing `NetworkXProvider.isInternetConnectedLiveData`

````
    NetworkXProvider.isInternetConnectedLiveData.observe(this) { status ->
        status?.let {
            textView.text = "Internet connection status: $it"
        }
    }
````

- To get connected network speed/last known speed call `NetworkXProvider.lastKnownSpeed` which return an `LastKnownSpeed` object

````
    NetworkXProvider.lastKnownSpeed?.let {
        textView2.text =
            "Last Known Speed: Speed - ${it.speed} | Type - ${it.networkTypeNetwork} | Simplified Speed - ${it.simplifiedSpeed}"
    }
````

- To obsever current network speed/last known speed start observing `NetworkXProvider.lastKnownSpeedLiveData`

````
    NetworkXProvider.lastKnownSpeedLiveData.observe(this) {
        it?.let {
            textView2.text =
                "Last Known Speed: Speed - ${it.speed} | Type - ${it.networkTypeNetwork} | Simplified Speed - ${it.simplifiedSpeed}"
        }
    }
````

 ## Notes:
 - NetworkX (including Speed Meter) only works when the app is in the foreground
 - The default value for `NetworkXProvider.isInternetConnected` is `false`
 - The default value for `NetworkXProvider.isInternetConnectedLiveData` is `null`
 - The default value for `NetworkXProvider.lastKnownSpeed` is `null`
 - The default value for `NetworkXProvider.lastKnownSpeedLiveData` is `null`

---

### How to show the `NoInternetDialog`?

```kotlin
    NoInternetDialog
        .Companion
        .Builder()
        // Provide activity reference
        .withActivity(this)
        // Provide custom title
        .withTitle("No internet!")
        // Provide custom mesage
        .withMessage("Your device is not connected to the internet!")
        // Register for callback
        .withActionCallback {
            // User clicked `Retry` button
        }
        .build()
        .show()
```

* Also, you can determine if the `NoInternetDialog` is currently visible or not by calling this method `NoInternetDialog.isDialogVisible()` which return an `Boolean`.

---

### Happy Coding....

---

### Contact me

[LinkedIn](https://www.linkedin.com/in/rommansabbir/)

---

### License

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

````html
Copyright (C) 2021 Romman Sabbir

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
