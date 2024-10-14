package com.adyen.android.assignment.api.places.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val icon: Photo,
    val id: Int,
    val name: String,
) : java.io.Serializable
