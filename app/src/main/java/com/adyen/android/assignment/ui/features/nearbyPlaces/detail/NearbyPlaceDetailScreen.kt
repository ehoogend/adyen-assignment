package com.adyen.android.assignment.ui.features.nearbyPlaces.detail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.features.nearbyPlaces.NearbyPlacePhoto

@Composable
fun NearbyPlaceDetailRoute(
    place: Place,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Column(
            Modifier.fillMaxSize()
        ) {
            NearbyPlacePhoto(
                place,
                animatedContentScope = animatedContentScope
            )
            Text(
                modifier = Modifier.sharedElement(
                    sharedTransitionScope.rememberSharedContentState(key = "name-${place.fsqId}"),
                    animatedVisibilityScope = animatedContentScope
                ),
                text = place.name,
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}