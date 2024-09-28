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
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.features.nearbyPlaces.component.CategoriesBlock
import com.adyen.android.assignment.ui.features.nearbyPlaces.component.PlaceInfoRow
import com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component.HorizontalPagerHeader

@Composable
fun NearbyPlaceDetailRoute(
    onNavigateUp: () -> Unit,
    place: Place,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    NearbyPlaceDetailScreen(
        onNavigateUp = onNavigateUp,
        place = place,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope
    )
}

@Composable
private fun NearbyPlaceDetailScreen(
    onNavigateUp: () -> Unit,
    place: Place,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                HorizontalPagerHeader(
                    place = place,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
                NavigateUpIcon(onNavigateUp = onNavigateUp)
            }
            DetailScreenContent(
                place = place,
                animatedContentScope = animatedContentScope,
                sharedTransitionScope = sharedTransitionScope,
            )
        }
    }
}

@Composable
private fun NavigateUpIcon(
    onNavigateUp: () -> Unit,
) {
    FilledIconButton(
        modifier = Modifier
            .systemBarsPadding()
            .padding(top = 8.dp, start = 8.dp),
        onClick = onNavigateUp
    ) {
        Icon(
            Icons.AutoMirrored.Filled.NavigateBefore,
            contentDescription = stringResource(
                R.string.navigate_up_icon
            )
        )
    }
}

@Composable
private fun DetailScreenContent(
    place: Place,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
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
            AddressText(place)
            Spacer(modifier = Modifier.padding(8.dp))
            PlaceInfoRow(
                place = place,
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "info-${place.fsqId}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .align(Alignment.CenterHorizontally)
            )
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            place.description?.let {
                DescriptionBlock(it)
            }
            place.categories.takeIf { it.isNotEmpty() }?.let { categories ->
                CategoriesBlock(
                    modifier = Modifier.sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "categories-${place.fsqId}"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    categories = categories
                )
            }
        }
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

