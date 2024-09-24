package com.adyen.android.assignment.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Geocodes(
    val main: Geocode
)

@Serializable
data class Geocode(
    val latitude: Double,
    val longitude: Double,
)