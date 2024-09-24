package com.adyen.android.assignment.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val icon: Icon,
    val id: Int,
    val name: String,
)
