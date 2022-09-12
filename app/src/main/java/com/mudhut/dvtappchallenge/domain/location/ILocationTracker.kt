package com.mudhut.dvtappchallenge.domain.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng

interface ILocationTracker {
    suspend fun getCurrentLocation():LatLng?
}
