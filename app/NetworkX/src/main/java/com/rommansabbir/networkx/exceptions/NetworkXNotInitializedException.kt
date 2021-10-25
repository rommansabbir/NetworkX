package com.rommansabbir.networkx.exceptions

@Deprecated("Deprecated. Use NetworkXProvider instead")
class NetworkXNotInitializedException :
    Exception("Did you initialized NetworkXCore from onCreate() in your application class?") {
}