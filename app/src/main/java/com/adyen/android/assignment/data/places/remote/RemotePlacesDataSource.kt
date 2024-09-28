package com.adyen.android.assignment.data.places.remote

import android.location.Location
import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.api.model.Place
import javax.inject.Inject

class RemotePlacesDataSource @Inject constructor(private val placesService: PlacesService) {

    suspend fun getRecommendedPlaces(location: Location?): List<Place> {
        val recommendations = placesService.getVenueRecommendations(
            VenueRecommendationsQueryBuilder()
                .apply {
                    location?.let {
                        setLatitudeLongitude(location.latitude, location.longitude)
                    }
                }.build()
        )
        return recommendations.results
    }
}
