package com.adyen.android.assignment.data.places.model

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
    @SerialName("closed_bucket")
    val closedBucket: ClosedBucket,
    val tel: String? = null,
    val website: String? = null,
    val email: String? = null,
    val price: Int? = null,
) : java.io.Serializable {

    val hasContactInfo = tel != null || website != null || email != null

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
            photos = listOf(
                Photo(
                    prefix = "prefix1",
                    suffix = "suffix1"
                ),
                Photo(
                    prefix = "prefix2",
                    suffix = "suffix2"
                )
            ),
            closedBucket = ClosedBucket.LikelyOpen,
            tel = "123456789",
            website = "https://www.website.com",
            email = null,
            price = 2,
        )
    }
}
