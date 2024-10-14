package com.adyen.android.assignment.ui.features.nearbyPlaces.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.api.places.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme
import kotlinx.collections.immutable.toImmutableList

@Composable
fun NearbyPlaceItem(
    place: Place,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        with(sharedTransitionScope) {
            val photo = remember { place.photos?.firstOrNull() }
            if (place.categories.isNotEmpty() || photo != null) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NearbyPlacePhoto(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        place = place
                    )
                    CategoriesBlock(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 8.dp, end = 8.dp)
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "categories-${place.fsqId}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        categories = place.categories.toImmutableList()
                    )
                }
            }
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
                PlaceInfoRow(
                    place = place,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "info-${place.fsqId}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .align(Alignment.End)
                )
            }
        }
    }
}

@Preview
@Composable
@SuppressLint("UnusedContentLambdaTargetStateParameter")
internal fun NearbyPlaceItemPreview() {
    AppTheme {
        SharedTransitionLayout {
            AnimatedContent(
                contentKey = { "content" },
                targetState = Unit,
            ) { _ ->
                NearbyPlaceItem(
                    place = Place.preview,
                    onClick = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@AnimatedContent,
                )
            }
        }
    }
}
