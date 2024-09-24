package com.adyen.android.assignment.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val categories: List<Category>,
    val distance: Int,
    val geocodes: Geocodes,
    val location: Location,
    val name: String,
    val timezone: String,
)