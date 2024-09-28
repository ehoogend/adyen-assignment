package com.adyen.android.assignment.ui.features.nearbyPlaces.detail.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

@Composable
fun HorizontalPagerHeader(
    place: Place,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    place.photos?.let { photos ->
        with(sharedTransitionScope) {
            Box {
                val pagerState: PagerState = rememberPagerState(
                    pageCount = { photos.size }
                )
                HorizontalPager(
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "photo-${place.fsqId}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .clip(MaterialTheme.shapes.medium.copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)),
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
                PageIndicators(pagerState)
            }
        }
    }
}

@Composable
private fun BoxScope.PageIndicators(pagerState: PagerState) {
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