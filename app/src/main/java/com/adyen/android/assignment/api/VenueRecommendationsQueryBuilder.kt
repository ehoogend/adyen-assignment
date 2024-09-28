package com.adyen.android.assignment.api

class VenueRecommendationsQueryBuilder : PlacesQueryBuilder() {
    private var latitudeLongitude: String? = null

    fun setLatitudeLongitude(latitude: Double, longitude: Double): VenueRecommendationsQueryBuilder {
        this.latitudeLongitude = "$latitude,$longitude"
        return this
    }

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        latitudeLongitude?.apply { queryParams["ll"] = this }
        queryParams["fields"] = "fsq_id,categories,distance,geocodes,location,name,rating,description,photos,closed_bucket,tel,website,email,price"
        queryParams["limit"] = "50"
    }
}
