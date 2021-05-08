package com.example.newsapp.ui

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.N)
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
                // No Connection
                binding.apply {
                    networkConnection.switchVisibility(
                        ivNoConnection,
                        bottomNavigationView,
                        btnRetry,
                        tvConnectionLost
                    )
                }
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
        settingFragment = SettingFragment()
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
                    binding.apply {
                        networkConnection.switchVisibility(
                            ivNoConnection,
                            bottomNavigationView,
                            btnRetry,
                            tvConnectionLost
                        )
                    }
                    if (userPreferences.readData(Constants.STATUS)) mp.start()
                    supportFragmentManager.createFragment(homeFragment, R.id.frame_layout)
                }
            } else {
                // No Connection
                Coroutines.main {
                    toast(R.string.connection_lost)
                }
            }
        }
    }
}