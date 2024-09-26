package com.adyen.android.assignment.ui.features.nearbyPlaces.detail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Place
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
                    HorizontalPagerHeader(
                        place = place,
                        animatedContentScope = animatedContentScope
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
private fun SharedTransitionScope.HorizontalPagerHeader(
    place: Place,
    animatedContentScope: AnimatedContentScope,
) {
    place.photos?.let { photos ->
        Box {
            val pagerState: PagerState = rememberPagerState(
                pageCount = { photos.size }
            )
            HorizontalPager(
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "photo-${place.fsqId}"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                state = pagerState,
                beyondViewportPageCount = 1,
            ) { page ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${photos[page].prefix}original${photos[page].suffix}")
                        .memoryCachePolicy(CachePolicy.ENABLED).build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = stringResource(
                        R.string.place_photo_content_description,
                        place.name
                    ),
                    placeholder = painterResource(R.drawable.landscape_placeholder),
                    fallback = painterResource(R.drawable.landscape_placeholder),
                )
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = animateColorAsState(
                        targetValue = if (pagerState.currentPage == iteration) Color.White else Color.Gray
                    )
                    val size = animateDpAsState(
                        targetValue = if (pagerState.currentPage == iteration) 16.dp else 12.dp
                    )
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color.value)
                            .size(size.value)
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

