package com.adyen.android.assignment.ui.features.nearbyPlaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        NearbyPlacePhoto(place = place)
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.align(Alignment.End),
                text = stringResource(R.string.distance_format, place.distance),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun NearbyPlacePhoto(place: Place, modifier: Modifier = Modifier) {
    Box {
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
            modifier = modifier
                .fillMaxWidth()
                .height(120.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.landscape_placeholder),
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