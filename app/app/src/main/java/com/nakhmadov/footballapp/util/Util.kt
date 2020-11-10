package com.nakhmadov.footballapp.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

fun isConnectedInternet(application: Application): Boolean {
    val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkInfo = connectivityManager.activeNetworkInfo
    return (networkInfo != null) && networkInfo.isConnectedOrConnecting
}