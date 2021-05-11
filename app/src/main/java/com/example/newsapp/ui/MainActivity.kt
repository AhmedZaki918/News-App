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
import com.example.newsapp.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: BaseFragment
    private lateinit var wishlistFragment: WishlistFragment
    private lateinit var settingFragment: SettingFragment

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
            if (networkConnection.isConnected) {
                if (userPreferences.readData(Constants.STATUS)) mp.start()
                supportFragmentManager.createFragment(homeFragment, R.id.frame_layout)
            } else {
                switchVisibility()
            }
        }
    }


    override fun onClick(v: View) {
        if (v.id == R.id.btn_retry) retryConnection()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        supportFragmentManager.apply {
            when (item.itemId) {
                R.id.ic_home -> createFragment(homeFragment, R.id.frame_layout)
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
        homeFragment = BaseFragment()
        wishlistFragment = WishlistFragment()
        settingFragment = SettingFragment(userPreferences)
        binding.apply {
            bottomNavigationView.setOnNavigationItemSelectedListener(this@MainActivity)
            btnRetry.setOnClickListener(this@MainActivity)
        }
    }


    private fun retryConnection() {
        networkConnection.registerNetworkCallback()
        Coroutines.background {
            // If there's a network available
            if (networkConnection.isConnected) {
                Coroutines.main {
                    switchVisibility()
                    if (userPreferences.readData(Constants.STATUS)) mp.start()
                    supportFragmentManager.createFragment(homeFragment, R.id.frame_layout)
                }
            } else {
                Coroutines.main {
                    toast(R.string.connection_lost)
                }
            }
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