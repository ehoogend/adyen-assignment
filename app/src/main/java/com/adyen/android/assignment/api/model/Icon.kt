package com.adyen.android.assignment.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Icon(
    val prefix: String,
    val suffix: String
)
