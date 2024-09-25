package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Category
import com.adyen.android.assignment.api.model.Geocode
import com.adyen.android.assignment.api.model.Geocodes
import com.adyen.android.assignment.api.model.Location
import com.adyen.android.assignment.api.model.Photo
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.theme.AppTheme

@Composable
fun NearbyPlaceItem(
    place: Place,
    onClick: () -> Unit,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        border = if (darkTheme) BorderStroke(1.dp, MaterialTheme.colorScheme.outline) else null
    ) {
        Row {
            NearbyPlacePhoto(place)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                place.description?.let {
                    Text(
                        text = place.description,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
private fun NearbyPlacePhoto(place: Place) {
    Box {
        val photo = place.photos?.firstOrNull()
        val painter = photo?.let {
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${photo.prefix}original${photo.suffix}")
                    .memoryCachePolicy(CachePolicy.ENABLED).build(),
            )
        } ?: painterResource(R.drawable.landscape_placeholder)
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            painter = painter,
            contentDescription = stringResource(R.string.placeholder_photo)
        )
    }
}

@Preview
@Composable
private fun NearbyPlaceItemPreview() {
    AppTheme {
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
        )
    }
}