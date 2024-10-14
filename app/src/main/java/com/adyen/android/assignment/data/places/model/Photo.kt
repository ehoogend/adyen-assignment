package com.adyen.android.assignment.data.places.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String? = null,
    val prefix: String,
    val suffix: String,
    val width: Int? = null,
    val height: Int? = null,
) : java.io.Serializable
