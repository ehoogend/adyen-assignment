package com.adyen.android.assignment.ui.features.nearbyPlaces.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.places.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun NearbyPlacePhoto(
    place: Place,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        val photo = remember { place.photos?.firstOrNull() }
        photo?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${photo.prefix}original${photo.suffix}")
                    .memoryCachePolicy(CachePolicy.ENABLED).build(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(
                    R.string.place_photo_content_description,
                    place.name
                ),
                placeholder = painterResource(R.drawable.landscape_placeholder),
                fallback = painterResource(R.drawable.landscape_placeholder),
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "photo-${place.fsqId}"),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview
@Composable
private fun NearbyPlacePhotoPreview() {
    AppTheme {
        SharedTransitionLayout {
            AnimatedContent(
                contentKey = { "content" },
                targetState = Unit,
            ) { _ ->
                Surface {
                    NearbyPlacePhoto(
                        place = Place.preview,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@AnimatedContent
                    )
                }
            }
        }
    }
}
