package com.example.newsapp.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.home.BaseFragment
import com.example.newsapp.ui.settings.SettingFragment
import com.example.newsapp.ui.wishlist.WishlistFragment
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {


    private lateinit var mp: MediaPlayer
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: BaseFragment
    private lateinit var wishlistFragment: WishlistFragment
    private lateinit var settingFragment: SettingFragment

    @Inject
    lateinit var userPreferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable sound effect
        mp = MediaPlayer.create(this, R.raw.intro)
        if (userPreferences.readData(Constants.STATUS)) {
            mp.start()
        }
        // Initialization
        homeFragment = BaseFragment()
        wishlistFragment = WishlistFragment()
        settingFragment = SettingFragment()
        createFragment(homeFragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_home -> createFragment(homeFragment)
            R.id.ic_favourite -> createFragment(wishlistFragment)
            R.id.ic_setting -> createFragment(settingFragment)
        }
        return true
    }


    private fun createFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
}