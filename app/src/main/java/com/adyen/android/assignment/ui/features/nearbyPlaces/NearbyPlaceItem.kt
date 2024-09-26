package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.api.model.Category
import com.adyen.android.assignment.api.model.Geocode
import com.adyen.android.assignment.api.model.Geocodes
import com.adyen.android.assignment.api.model.Location
import com.adyen.android.assignment.api.model.Photo
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.features.nearbyPlaces.component.DistanceComponent
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun NearbyPlaceItem(
    place: Place,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        with(sharedTransitionScope) {
            NearbyPlacePhoto(
                modifier = Modifier.sharedElement(
                    rememberSharedContentState(key = "photo-${place.fsqId}"),
                    animatedVisibilityScope = animatedContentScope
                ),
                place = place,
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(key = "name-${place.fsqId}"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    text = place.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                DistanceComponent(
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "distance-${place.fsqId}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .align(Alignment.End),
                    distance = place.distance
                )
            }
        }
    }
}

@Preview
@Composable
private fun NearbyPlaceItemPreview() {
    AppTheme {
        SharedTransitionLayout {
            AnimatedContent(
                contentKey = { "content" },
                targetState = Unit,
            ) { _ ->
                NearbyPlaceItem(
                    place = Place(
                        fsqId = "1",
                        name = "Place name 1",
                        categories = listOf(
                            Category(
                                name = "Category 1",
                                icon = Photo(
                                    prefix = "prefix",
                                    suffix = "suffix"
                                ),
                                id = 1
                            )
                        ),
                        distance = 123,
                        geocodes = Geocodes(
                            main = Geocode(
                                latitude = 1.0,
                                longitude = 1.0
                            ),
                        ),
                        location = Location(
                            address = "Address 1",
                            country = "Netherlands"
                        ),
                        timezone = "Europe/Amsterdam",
                        description = "Place description 1",
                        photos = null
                    ),
                    onClick = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@AnimatedContent,
                )
            }
        }
    }
}