package com.adyen.android.assignment.data.places

import android.location.Location
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.data.places.remote.RemotePlacesDataSource
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val placesRemoteDataSource: RemotePlacesDataSource) {

    /**
     * Fetches a list of venues near the specified location.
     *
     * @param location The user's current location.
     * @return A [List] of [Place] objects.
     */
    suspend fun getRecommendedPlaces(location: Location): List<Place> {
        return placesRemoteDataSource.getRecommendedPlaces(location)
    }
}