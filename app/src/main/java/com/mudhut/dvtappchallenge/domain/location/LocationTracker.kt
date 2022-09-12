package com.mudhut.dvtappchallenge.domain.location

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
class LocationTracker @Inject constructor(
    private val locationProviderClient: FusedLocationProviderClient
) : ILocationTracker {
    override suspend fun getCurrentLocation(): LatLng? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            locationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cancellableContinuation.resume(LatLng(result.latitude, result.longitude))
                    } else {
                        cancellableContinuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cancellableContinuation.resume(LatLng(it.latitude, it.longitude))
                }
                addOnFailureListener {
                    cancellableContinuation.resume(null)
                }
                addOnCanceledListener {
                    cancellableContinuation.cancel()
                }
            }
        }
    }
}
