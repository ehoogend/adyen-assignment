package com.adyen.android.assignment.ui.features.nearbyPlaces.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.adyen.android.assignment.api.model.Place

@Composable
fun NearbyPlaceDetailRoute(place: Place) {
    Text(text = place.name, style = MaterialTheme.typography.headlineMedium)
}