package com.example.newsapp.util

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
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


    // Switch visibility for views depends on network state
    fun switchVisibility(
        image: ImageView,
        bottomNavigation: BottomNavigationView,
        btnRetry: Button,
        connection: TextView
    ) {
        if (isConnected) {
            image.visibility = GONE
            btnRetry.visibility = GONE
            connection.visibility = GONE
            bottomNavigation.visibility = VISIBLE

        } else {
            image.visibility = VISIBLE
            btnRetry.visibility = VISIBLE
            connection.visibility = VISIBLE
            bottomNavigation.visibility = GONE
        }
    }
}