package com.adyen.android.assignment.data.places.remote

import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.api.model.Place
import javax.inject.Inject


class RemotePlacesDataSource @Inject constructor(private val placesService: PlacesService) {

    //TODO: Add device location to request if available
    suspend fun getRecommendedPlaces(): List<Place> {
        val recommendations = placesService.getVenueRecommendations(
            VenueRecommendationsQueryBuilder().build()
        )
        return recommendations.results
    }
}