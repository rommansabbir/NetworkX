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
| 3.4.1         |


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
- To check Internet Connection status, simply call extension variable
`isInternetConnected` or `isInternetConnectedLiveData` or `isInternetConnectedFlow`.
````
isInternetConnectedFlow.collectLatest {
    lifecycleScope.launch {
            textView.text = "Internet connection status: $it"
        }
    }
````

- To get connected network speed/last known speed [`LastKnownSpeed`] call extension variable
`lastKnownSpeed` or `lastKnownSpeedLiveData` or `lastKnownSpeedFlow`

````
lastKnownSpeed?.let {
    textView2.text ="S-${it.speed}|T-${it.networkTypeNetwork}|SS-${it.simplifiedSpeed}"
}
````

 ## Notes:
 - **NetworkX** (including **Speed Meter**) only works when the **Application** is in the **Forground Only**.
 - To emit (**`MutableStateFlow`**) **Last Known Speed** or **Internet Connection Status**,required **`CoroutineScope`** works under a **`Dispatchers.IO`** context.
 - The default value for **Internet Connection Status** is `false`.
 - The default value for **LastKnownSpeed** is `NONE`.

---

### How to show the **`NoInternetDialogV2`**?

```kotlin
NoInternetDialogV2(
    activity = WeakReference(this@MainActivity),
    title = "No Internet Bro",
    message = "This is just a dummy message",
    buttonTitle = "Okay",
    isCancelable = true
) { /* Button Presses */ }
```

* Also, you can determine if the `NoInternetDialogV2` is currently visible or not by calling this variable `NoInternetDialogV2.isVisible` which return an `Boolean`.

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
