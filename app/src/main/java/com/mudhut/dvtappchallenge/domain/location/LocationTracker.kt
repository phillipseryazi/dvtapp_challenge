package com.mudhut.dvtappchallenge.domain.location

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
class LocationTracker @Inject constructor(
    private val locationProviderClient: FusedLocationProviderClient
) : ILocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            locationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cancellableContinuation.resume(result)
                    } else {
                        cancellableContinuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cancellableContinuation.resume(it)
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
