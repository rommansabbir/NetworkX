package com.rommansabbir.networkx.exceptions

class NetworkXNotInitializedException :
    Exception("Did you initialized NetworkX with NetworkXConfig from onCreate() in your application class?")