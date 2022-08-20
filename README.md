![NetworkX](https://user-images.githubusercontent.com/25950083/185731068-480fd969-f18d-439c-938a-6285a50c2be2.png)

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

 <p align="center">
    <a href="https://android-arsenal.com/details/1/8160"><img alt="Maintained" src="https://img.shields.io/badge/Android%20Arsenal-NetworkX-green.svg?style=flat" height="20"/></a>
</p>

 <p align="center">
     <a href="https://github.com/rommansabbir/NetworkX"><img alt="Maintained" src="https://img.shields.io/badge/Maintained_Actively%3F-Yes-green.svg" height="20"/></a>
</p>

 <p align="center">
     <a href="https://jitpack.io/#rommansabbir/NetworkX"><img alt="JitPack" src="https://img.shields.io/badge/JitPack-Yes-green.svg?style=flat" height="20"/></a>
</p>

<h1 align="center"> ⚡ Latest Version: 4.1.0 | Change Logs 🔰</h1>

- NetworkX now works with both __Activity__ or __Application__ Scope (NetworkX lifecycle is bounded to `NetworkXLifecycle.Activity` or `NetworkXLifecycle.Application`)
- Introduced `SmartConfig` to replace old config [`NetworkXConfig` has been deprecated]
- New __API__ to initialize `NetworkX`, enabled smart refactoring to replace old __API__ with new one
- New __API__ [`NoInternetDialogV2.forceClose()`] added to close `Dialog` forcefully
- Added support for custom _Drawable_ to be shown in `NoInternetDialogV2`
- Several __Classes__, __APIs__ has been deprecated.
- Removed unused classes/packages
- Colorful Documentation 😂

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

<h1 align="center">Installation</h1>

## ➤ Step 1:

Add the JitPack repository to your build file .

```gradle
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

## ➤ Step 2:

Add the dependency.

```gradle
    dependencies {
            implementation 'com.github.rommansabbir:NetworkX:4.1.0'
    }
```

## ➤ Step 3:
Initialize `NetworkX` from your `Application.onCreate()`
````
    //Deprecated way
    val builder = NetworkXConfig.Builder()
        .withApplication(this)
        // You can disable speed meter if not required
        .withEnableSpeedMeter(true)
        .build()
    NetworkXProvider.enable(builder)

    //New smart way
    NetworkXProvider.enable(SmartConfig(this, true, NetworkXLifecycle.Application))
````

## ➤ Step 4:
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

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)


<h1 align="center">NoInternetDialogV2</h1>

- Show Dialog
```kotlin
    NoInternetDialogV2(
        activity = WeakReference(this@MainActivity),
        title = "No Internet Bro",
        message = "This is just a dummy message",
        buttonTitle = "Okay",
        isCancelable = true
    ) { /* Button Presses */ }
```
- Close Dialog (Forcefully)

```kotlin
    NoInternetDialogV2.forceClose()
```
- Determine if the `NoInternetDialogV2` is currently visible or not.

```kotlin
    NoInternetDialogV2.isVisible
```

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)


<h1 align="center">Notes:</h1>

 - **NetworkX** (including **Speed Meter**) can work on both **Application** scope or **Activity** scope. If scope is **Activity**, NetworkX will *start/release* it's components based on _ActivityLifecycleCallback_ (**onCreate - onDestroy**). Else, it will *start* it's components only once and there will be no components *release* event.
 - To emit (**`MutableStateFlow`**) **Last Known Speed** or **Internet Connection Status**,required **`CoroutineScope`** works under a **`Dispatchers.IO`** context.
 - The default value for **Internet Connection Status** is `false`.
 - The default value for **LastKnownSpeed** is `NONE`.


[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

## Contact me

✔ [LinkedIn](https://www.linkedin.com/in/rommansabbir/)

✔ [Website](https://rommansabbir.com)


[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

### License

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

````html
Copyright (C) 2022 Romman Sabbir

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
