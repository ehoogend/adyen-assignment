package com.adyen.android.assignment.api.places.model

import kotlinx.serialization.Serializable

@Serializable
data class PlacesResponse(
    val results: List<Place>,
)
