package com.example.newsapp.util

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import javax.inject.Inject

@Suppress("DEPRECATION")
class NetworkConnection @Inject constructor(private var connectivityManager: ConnectivityManager) {


    var isConnected = false

    fun registerNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    isConnected = true
                }

                override fun onLost(network: Network) {
                    isConnected = false
                }
            })
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            isConnected = networkInfo != null && networkInfo.isConnected
        }
    }


    fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(NetworkCallback())
    }
}