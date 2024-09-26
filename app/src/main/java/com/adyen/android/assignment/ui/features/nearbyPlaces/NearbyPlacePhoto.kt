package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun SharedTransitionScope.NearbyPlacePhoto(
    place: Place,
    modifier: Modifier = Modifier,
    animatedContentScope: AnimatedContentScope,
) {
    Box(
        modifier = modifier
            .sharedElement(
                rememberSharedContentState(key = "photo-${place.fsqId}"),
                animatedVisibilityScope = animatedContentScope
            )
            .clip(MaterialTheme.shapes.medium),
    ) {
        val photo = remember { place.photos?.firstOrNull() }
        photo?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${photo.prefix}original${photo.suffix}")
                    .memoryCachePolicy(CachePolicy.ENABLED).build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = stringResource(
                    R.string.place_photo_content_description,
                    place.name
                ),
                placeholder = painterResource(R.drawable.landscape_placeholder),
                fallback = painterResource(R.drawable.landscape_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        } ?: Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.landscape_placeholder),
            contentDescription = stringResource(R.string.placeholder_photo)
        )
    }
}