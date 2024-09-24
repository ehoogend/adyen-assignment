package com.adyen.android.assignment.data.repository

import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.data.places.remote.RemotePlacesDataSource
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val placesRemoteDataSource: RemotePlacesDataSource) {
    //TODO: Add device location to request if available
    suspend fun getRecommendedPlaces(): List<Place> {
        return placesRemoteDataSource.getRecommendedPlaces()
    }
}