package com.rommansabbir.networkx.exceptions

@Deprecated("Deprecated. Use NetworkXProvider instead")
class NetworkXUpdateStrategyException :
    Exception("Failed to update strategy. Make sure the observation is not cancelled.")