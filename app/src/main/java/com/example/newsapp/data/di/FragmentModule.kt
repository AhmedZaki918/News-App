package com.example.newsapp.data.di

import com.example.newsapp.ui.home.BaseFragment
import com.example.newsapp.ui.settings.SettingFragment
import com.example.newsapp.ui.wishlist.WishlistFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FragmentModule {


    @Provides
    @Singleton
    fun providesBaseFragment() = BaseFragment()


    @Provides
    @Singleton
    fun providesWishlistFragment() = WishlistFragment()


    @Provides
    @Singleton
    fun providesSettingsFragment() = SettingFragment()


    @Provides
    fun provideString() = ""
}