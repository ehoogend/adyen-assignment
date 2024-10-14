package com.adyen.android.assignment.api.places.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val address: String? = null,
    val country: String,
    val locality: String? = null,
    val neighborhood: List<String>? = null,
    val postcode: String? = null,
    val region: String? = null,
) : java.io.Serializable
