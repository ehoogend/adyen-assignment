package com.adyen.android.assignment.api.places.model

import com.adyen.android.assignment.data.places.model.Place
import kotlinx.serialization.Serializable

@Serializable
data class PlacesResponse(
    val results: List<Place>,
)
