package com.adyen.android.assignment.data.places.remote

import android.location.Location
import com.adyen.android.assignment.api.places.PlacesService
import com.adyen.android.assignment.api.places.model.Place
import javax.inject.Inject

class RemotePlacesDataSource @Inject constructor(private val placesService: PlacesService) {

    /**
     * Fetches a list of venues from the Foursquare API.
     *
     * @param location The user's current location.
     * @return A [List] of [Place] objects fetched from the API.
     */
    suspend fun getRecommendedPlaces(location: Location): List<Place> {
        val recommendations = placesService.getNearbyPlaces(
            fields = "fsq_id,categories,distance,geocodes,location,name,rating," +
                "description,photos,closed_bucket,tel,website,email,price",
            limit = 50,
            location = "${location.latitude},${location.longitude}"
        )
        return recommendations.results
    }
}
