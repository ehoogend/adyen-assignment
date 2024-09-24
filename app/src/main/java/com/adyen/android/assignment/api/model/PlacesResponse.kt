package com.adyen.android.assignment.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PlacesResponse(
    val results: List<Place>,
)
