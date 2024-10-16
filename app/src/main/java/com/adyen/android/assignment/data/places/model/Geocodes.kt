package com.adyen.android.assignment.data.places.model

import kotlinx.serialization.Serializable

@Serializable
data class Geocodes(
    val main: Geocode
) : java.io.Serializable

@Serializable
data class Geocode(
    val latitude: Double,
    val longitude: Double,
) : java.io.Serializable
