package com.adyen.android.assignment.ui.features.nearbyPlaces.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
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
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.features.nearbyPlaces.component.CategoriesBlock
import com.adyen.android.assignment.ui.features.nearbyPlaces.component.PlaceInfoRow
import com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component.AddressText
import com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component.ContactInfoBlock
import com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component.DescriptionBlock
import com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component.HorizontalPagerHeader
import com.adyen.android.assignment.ui.theme.AppTheme
import kotlinx.collections.immutable.toImmutableList

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
                NavigateUpIcon(
                    onNavigateUp = onNavigateUp,
                    modifier = Modifier
                        .systemBarsPadding()
                        .align(Alignment.TopStart)
                        .padding(top = 8.dp, start = 8.dp)
                )
                NavigateToPlaceIcon(
                    place = place,
                    modifier = Modifier
                        .systemBarsPadding()
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 8.dp)
                )
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
    modifier: Modifier = Modifier,
) {
    FilledIconButton(
        modifier = modifier,
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
private fun NavigateToPlaceIcon(
    place: Place,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    FilledIconButton(
        modifier = modifier,
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse(
                        "geo:0,0?q=${place.geocodes.main.latitude},${place.geocodes.main.longitude}(${place.name})"
                    )
            }
            context.startActivity(intent)
        }
    ) {
        Icon(
            Icons.Default.Navigation,
            contentDescription = stringResource(R.string.navigate_to_place_icon)
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
            Spacer(modifier = Modifier.padding(4.dp))
            PlaceInfoRow(
                place = place,
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "info-${place.fsqId}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .align(Alignment.CenterHorizontally)
            )
            place.categories.takeIf { it.isNotEmpty() }?.let { categories ->
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                CategoriesBlock(
                    modifier = Modifier.sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "categories-${place.fsqId}"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    categories = categories.toImmutableList()
                )
            }
            place.description?.let {
                Spacer(modifier = Modifier.padding(4.dp))
                DescriptionBlock(it)
            }
            if (place.hasContactInfo) {
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                ContactInfoBlock(place)
            }
        }
    }
}

@Composable
@Preview
@SuppressLint("UnusedContentLambdaTargetStateParameter")
internal fun NearbyPlaceDetailScreenPreview() {
    AppTheme {
        SharedTransitionLayout {
            AnimatedContent(
                contentKey = { "content" },
                targetState = Unit,
            ) { _ ->
                NearbyPlaceDetailScreen(
                    onNavigateUp = {},
                    place = Place.preview,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@AnimatedContent
                )
            }
        }
    }
}