package com.adyen.android.assignment.data.places.remote

import android.location.Location
import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.model.Place
import javax.inject.Inject

class RemotePlacesDataSource @Inject constructor(private val placesService: PlacesService) {

    suspend fun getRecommendedPlaces(location: Location): List<Place> {
        val recommendations = placesService.getVenueRecommendations(
            fields = "fsq_id,categories,distance,geocodes,location,name,rating," +
                "description,photos,closed_bucket,tel,website,email,price",
            limit = 50,
            location = "${location.latitude},${location.longitude}"
        )
        return recommendations.results
    }
}
