package com.adyen.android.assignment.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.adyen.android.assignment.api.model.Place
import com.adyen.android.assignment.ui.features.nearbyPlaces.NearbyPlacesRoute
import com.adyen.android.assignment.ui.features.nearbyPlaces.detail.NearbyPlaceDetailRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    SharedTransitionLayout {
        NavHost(
            navController,
            startDestination = Screen.NearbyPlaces
        ) {
            composable<Screen.NearbyPlaces> {
                NearbyPlacesRoute(
                    onClickPlace = { place ->
                        navController.navigate(Screen.NearbyPlaceDetail(place))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                )
            }
            composable<Screen.NearbyPlaceDetail>(
                typeMap = mapOf(typeOf<Place>() to PlaceType)
            ) { backstackEntry ->
                val nearbyPlaceDetail: Screen.NearbyPlaceDetail = backstackEntry.toRoute()
                NearbyPlaceDetailRoute(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    place = nearbyPlaceDetail.place,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                )
            }
        }
    }
}

@Serializable
private sealed class Screen {
    @Serializable
    data object NearbyPlaces : Screen()

    @Serializable
    data class NearbyPlaceDetail(val place: Place) : Screen()
}

private val PlaceType = object : NavType<Place>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Place? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getSerializable(key, Place::class.java)
        } else {
            @Suppress("DEPRECATION") // for backwards compatibility
            bundle.getSerializable(key) as? Place
        }

    override fun parseValue(value: String): Place {
        return Json.decodeFromString<Place>(Uri.decode(value))
    }

    override fun serializeAsValue(value: Place): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: Place) {
        bundle.putSerializable(key, value)
    }
}