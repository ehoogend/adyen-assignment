package com.adyen.android.assignment.ui

import android.os.Bundle
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
    NavHost(
        navController,
        startDestination = Screen.NearbyPlaces
    ) {
        composable<Screen.NearbyPlaces> {
            NearbyPlacesRoute(
                onClickPlace = { place ->
                    navController.navigate(Screen.NearbyPlaceDetail(place))
                }
            )
        }
        composable<Screen.NearbyPlaceDetail>(
            typeMap = mapOf(typeOf<Place>() to PlaceType)
        ) { backstackEntry ->
            val nearbyPlaceDetail: Screen.NearbyPlaceDetail = backstackEntry.toRoute()
            NearbyPlaceDetailRoute(nearbyPlaceDetail.place)
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

private val PlaceType = object : NavType<Place>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Place? {
        return bundle.getString(key)?.let { Json.decodeFromString<Place>(it) }
    }

    override fun parseValue(value: String): Place {
        return Json.decodeFromString<Place>(value)
    }

    override fun serializeAsValue(value: Place): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Place) {
        bundle.putSerializable(key, value)
    }
}