package com.example.newsapp.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.home.BaseFragment
import com.example.newsapp.ui.settings.SettingFragment
import com.example.newsapp.ui.wishlist.WishlistFragment
import com.example.newsapp.util.Coroutines
import com.example.newsapp.util.NetworkConnection
import com.example.newsapp.util.createFragment
import com.example.newsapp.util.toast
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var baseFragment: BaseFragment

    @Inject
    lateinit var settingFragment: SettingFragment

    @Inject
    lateinit var wishlistFragment: WishlistFragment

    @Inject
    lateinit var networkConnection: NetworkConnection

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var mp: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        // If there's a network available
        networkConnection.registerNetworkCallback()
        Coroutines.background {
            if (networkConnection.isConnected) goToHomeFragment {}
            else switchVisibility()
        }
    }


    override fun onClick(v: View) {
        if (v.id == R.id.btn_retry) retryConnection()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        supportFragmentManager.apply {
            when (item.itemId) {
                R.id.ic_home -> createFragment(baseFragment, R.id.frame_layout)
                R.id.ic_favourite -> createFragment(wishlistFragment, R.id.frame_layout)
                R.id.ic_setting -> createFragment(settingFragment, R.id.frame_layout)
            }
        }
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        networkConnection.unregisterNetworkCallback()
    }


    private fun initViews() {
        binding.apply {
            bottomNavigationView.setOnItemSelectedListener(this@MainActivity)
            btnRetry.setOnClickListener(this@MainActivity)
        }
    }


    private fun retryConnection() {
        networkConnection.registerNetworkCallback()
        Coroutines.background {
            if (networkConnection.isConnected) {
                goToHomeFragment { switchVisibility() }
            } else {
                Coroutines.main {
                    toast(R.string.connection_lost)
                }
            }
        }
    }


    private fun goToHomeFragment(method: () -> Unit?) {
        Coroutines.main {
            if (userPreferences.readData(Constants.STATUS)) mp.start()
            supportFragmentManager.createFragment(baseFragment, R.id.frame_layout)
            method()
        }
    }


    // Switch visibility for views depends on network state
    private fun switchVisibility() {
        binding.apply {
            if (networkConnection.isConnected) {
                ivNoConnection.visibility = GONE
                btnRetry.visibility = GONE
                tvConnectionLost.visibility = GONE
                bottomNavigationView.visibility = VISIBLE

            } else {
                ivNoConnection.visibility = VISIBLE
                btnRetry.visibility = VISIBLE
                tvConnectionLost.visibility = VISIBLE
                bottomNavigationView.visibility = GONE
            }
        }
    }
}