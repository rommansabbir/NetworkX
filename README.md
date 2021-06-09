[![Release](https://jitpack.io/v/jitpack/android-example.svg)](https://jitpack.io/#rommansabbir/NetworkX)

# NetworkX

An easy & handy library to monitor device internet connection status 

## Features

* The real-time update on internet connection status changed
* Lightweight
* Provide both regular data or Live Data to observe from a fragment or an activity
* Thread Safe
* Cancel monitoring Internet connection status any time
* Restart monitoring Internet connection status if cancelled
* Show NoInternetDialog to the user

---

## How does it work?

Once you start observing internet connection status by calling from your ``Application`` class `onCreate()`
method, ``NetworkX`` will start monitor internet connection status immediately under a `CoroutineScope`.

``NetworkX`` provide both traditional data which is `Boolean` or `LiveData`
so that you can observe the status in a fragment or an activity.

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
| 2.2.0         |

### What's new in this version?

* `NoInternetDialog` added to show a No Internet Dialog to the user [You must enable `dataBinding.enabled = true` to use this `NoInternetDialog`]
* Performance Improved
* Dependencies updated to the latest version
* New functionality added , `restartObservation` and `updateStrategy`

# Usages

## Start observing internet connection status from your `Application` class

To Start observing network status using ``NetworkX`` call `NetworkXCore.init()` from application `onCreate` method.
Otherwise, when try to check if device is connected to the internet or not it will throw an `RuntimeException` called `NetworkXNotInitializedException`.

Provide `Application` reference & `NetworkXObservingStrategy` to `NetworkX` .

`NetworkXObservingStrategy` represent delay time in internet connection status checking
Sates are:

* `NetworkXObservingStrategy.LOW` (15 * 1000) (Millis)
* `NetworkXObservingStrategy.MEDIUM` (10 * 1000) (Millis)
* `NetworkXObservingStrategy.HIGH` (3 * 1000) (Millis)
* `NetworkXObservingStrategy.REALTIME` (1 * 1000) (Millis)

```kotlin
    override fun onCreate() {
        super.onCreate()
        NetworkXCore.init(this, NetworkXObservingStrategy.MEDIUM)
    }
```

---

### Also, you can provide custom time interval in millis

Custom State:

* NetworkXObservingStrategy.CUSTOM

````kotlin
    override fun onCreate() {
        super.onCreate()
        NetworkXCore.init(this, NetworkXObservingStrategy.CUSTOM(15 * 1000))
    }
````

---

#### How to check the connection status in a `Fragment` or an `Activity` or `Anywhere of the application`

* To get status of internet connection for single time, use `NetworkX.isInternetConnected()` method to retrieve connection status

````kotlin
    NetworkXCore.getNetworkX().isInternetConnected().let {
        when (it) {
            true -> {
                /**
                 * Do your stuff here when internet is connected
                 */
                Log.d("NetworkX", "Is internet connected: $it")
            }
            else -> {
                /**
                 * Do your stuff here when internet is not connected
                 */
                Log.d("NetworkX", "Is internet connected: $it")
            }
        }
    }
````

---

* To observe internet connection status, use `NetworkX.isInternetConnectedLiveData()` method to retrieve connection status wrapped into a `LiveData<Boolean>`.

````kotlin
    NetworkXCore.getNetworkX().isInternetConnectedLiveData().observe(
        viewLifeCycleOwner/activity),
        Observer {
            when (it) {
                true -> {
                    /**
                     * Do your stuff here when internet is connected
                     */
                    Log.d("NetworkX", "Is internet connected: $it")
                }
                else -> {
                    /**
                     * Do your stuff here when internet is not connected
                     */
                    Log.d("NetworkX", "Is internet connected: $it")
                }
            }
        }
    )
````

---

* If you want to cancel the observing if internet is connected status, use `NetworkX.cancelObservation()` method

````koltin
    NetworkXCore.getNetworkX().cancelObservation()
````

* If you want to restart the observing, use `NetworkX.restartObservation()` method

````koltin
    NetworkXCore.getNetworkX().restartObservation()
````

* If you want to update the strategy, use `NetworkX.updateStrategy()` method. Make sure that `NetworkXCore` is initialized properly and current observation status is not cancelled otherwise it will throw `RuntimeException` called `NetworkXUpdateStrategyException`

````koltin
    NetworkXCore.getNetworkX().restartObservation()
````

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

### Checkout the sample app for the implementaion in detail

---

### Contact me

[Portfolio](https://www.rommansabbir.com/) | [LinkedIn](https://www.linkedin.com/in/rommansabbir/) | [Twitter](https://www.twitter.com/itzrommansabbir/) | [Facebook](https://www.facebook.com/itzrommansabbir/)

---

### License

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

````html
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
````
