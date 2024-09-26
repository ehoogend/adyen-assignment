package com.adyen.android.assignment.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    @SerialName("fsq_id")
    val fsqId: String,
    val categories: List<Category>,
    val distance: Int,
    val geocodes: Geocodes,
    val location: Location,
    val name: String,
    val timezone: String,
    val description: String? = null,
    val photos: List<Photo>?,
) : java.io.Serializable
