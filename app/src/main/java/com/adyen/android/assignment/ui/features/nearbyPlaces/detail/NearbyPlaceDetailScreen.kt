package com.adyen.android.assignment.ui.features.nearbyPlaces.detail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.features.nearbyPlaces.NearbyPlacePhoto
import com.adyen.android.assignment.ui.features.nearbyPlaces.component.DistanceComponent

@Composable
fun NearbyPlaceDetailRoute(
    onNavigateUp: () -> Unit,
    place: Place,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    with(sharedTransitionScope) {
        Surface {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box {
                    NearbyPlacePhoto(
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "photo-${place.fsqId}"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                        place = place,
                    )
                    FilledIconButton(
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(8.dp),
                        onClick = onNavigateUp
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.navigate_up_icon
                            )
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "name-${place.fsqId}"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                        text = place.name,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    DetailScreenContent(
                        place = place,
                        animatedContentScope = animatedContentScope
                    )
                }
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.DetailScreenContent(
    place: Place,
    animatedContentScope: AnimatedContentScope
) {
    AddressText(place)
    Spacer(modifier = Modifier.padding(8.dp))
    DistanceComponent(
        modifier = Modifier.sharedElement(
            rememberSharedContentState(key = "distance-${place.fsqId}"),
            animatedVisibilityScope = animatedContentScope
        ),
        distance = place.distance,
    )
    HorizontalDivider(Modifier.padding(vertical = 8.dp))
    place.description?.let {
        DescriptionBlock(it)
    }
}

@Composable
private fun AddressText(place: Place) {
    val addressText =
        rememberSaveable(place) {
            listOfNotNull(
                place.location.address,
                place.location.locality,
                place.location.region,
                place.location.postcode
            ).joinToString(", ")
        }
    Text(
        text = addressText,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun DescriptionBlock(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

