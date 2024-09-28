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
    val rating: Float? = null,
    val description: String? = null,
    val photos: List<Photo>?,
) : java.io.Serializable {
    companion object {
        val preview: Place = Place(
            fsqId = "1",
            name = "Place name 1",
            categories = listOf(
                Category(
                    name = "Category 1",
                    icon = Photo(
                        prefix = "prefix",
                        suffix = "suffix"
                    ),
                    id = 1
                )
            ),
            distance = 123,
            geocodes = Geocodes(
                main = Geocode(
                    latitude = 1.0,
                    longitude = 1.0
                ),
            ),
            location = Location(
                address = "Address 1",
                country = "Netherlands"
            ),
            description = "Place description 1",
            rating = 4.5f,
            photos = null
        )
    }
}
