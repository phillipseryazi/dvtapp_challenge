package com.mudhut.dvtappchallenge.domain.location

import android.location.Location

interface ILocationTracker {
    suspend fun getCurrentLocation():Location?
}
