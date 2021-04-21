package com.example.newsapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.home.HomeFragment
import com.example.newsapp.data.local.Constants

class PagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        Constants.apply {
            return when (position) {
                0 -> {
                    HomeFragment(ALL)
                }
                1 -> {
                    HomeFragment(HEALTH)
                }
                2 -> {
                    HomeFragment(TECH)
                }
                else -> {
                    HomeFragment(SPORT)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return 4
    }
}