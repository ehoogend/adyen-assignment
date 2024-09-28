package com.adyen.android.assignment.data.repository

import android.location.Location
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.data.places.remote.RemotePlacesDataSource
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val placesRemoteDataSource: RemotePlacesDataSource) {

    suspend fun getRecommendedPlaces(location: Location?): List<Place> {
        return placesRemoteDataSource.getRecommendedPlaces(location).toImmutableList()
    }
}