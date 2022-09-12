package com.mudhut.dvtappchallenge.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.text.SimpleDateFormat

@SuppressLint("MissingPermission")
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var result = false
    connectivityManager.let {
        it.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            }
    }
    return result
}

@SuppressLint("SimpleDateFormat")
fun formatDate(millis: Long): String {
    val simpleDateFormat = SimpleDateFormat("EEEE")
    return simpleDateFormat.format(millis)
}
