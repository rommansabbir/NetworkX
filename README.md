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
| 3.0.0         |


# Usages

## Step 1:
Initialize `NetworkX` from your `Application.onCreate()` method by calling `NetworkXProvider.init()` which take `Application` reference as the param.

## Step 2:
- To check internet connection status, simply call `NetworkXProvider.isInternetConnected` which return a `Boolean` value.

- To observe the internet connection status, start observing `NetworkXProvider.isInternetConnectedLiveData`

 ## Note:
 - The default value for `NetworkXProvider.isInternetConnected` is `false`
 - The default value for `NetworkXProvider.isInternetConnectedLiveData` is `null`

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
