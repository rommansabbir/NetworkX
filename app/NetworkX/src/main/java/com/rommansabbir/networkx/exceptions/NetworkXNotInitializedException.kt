package com.rommansabbir.networkx.exceptions

class NetworkXNotInitializedException :
    Exception("Did you initialized NetworkXCore from onCreate() in your application class?") {
}