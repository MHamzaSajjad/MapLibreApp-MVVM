package com.mhamzasajjad.mapapp.domain.model.location

data class UserLocationDetails(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    var altitude: Double = 0.0,
)