package com.adyen.android.assignment.ui.features.nearbyPlaces

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.data.places.model.Place
import com.adyen.android.assignment.ui.features.nearbyPlaces.component.NearbyPlaceItem
import com.adyen.android.assignment.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun NearbyPlacesSuccessContent(
    places: ImmutableList<Place>,
    onClickPlace: (Place) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        columns = StaggeredGridCells.Adaptive(250.dp),
        verticalItemSpacing = 12.dp,
    ) {
        items(places, key = { place -> place.fsqId }) { place ->
            NearbyPlaceItem(
                place = place,
                onClick = { onClickPlace(place) },
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    }
}

@Preview
@Composable
@SuppressLint("UnusedContentLambdaTargetStateParameter")
internal fun NearbyPlacesSuccessPreview() {
    AppTheme {
        SharedTransitionLayout {
            AnimatedContent(
                contentKey = { "content" },
                targetState = Unit,
            ) { _ ->
                Scaffold {
                    NearbyPlacesSuccessContent(
                        modifier = Modifier.padding(it),
                        places = persistentListOf(Place.preview, Place.preview.copy(fsqId = "2")),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@AnimatedContent,
                        onClickPlace = {}
                    )
                }
            }
        }
    }
}
