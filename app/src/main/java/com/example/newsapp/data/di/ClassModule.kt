package com.example.newsapp.data.di

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.ConnectivityManager
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import com.example.newsapp.util.NetworkConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClassModule {


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideEditor(@ApplicationContext context: Context): SharedPreferences.Editor {
        return provideSharedPreferences(context).edit()
    }


    @Provides
    @Singleton
    fun provideUserPref(@ApplicationContext context: Context) =
        UserPreferences(provideSharedPreferences(context), provideEditor(context))


    @Provides
    fun provideMediaPlayer(@ApplicationContext context: Context): MediaPlayer {
        return MediaPlayer.create(context, R.raw.intro)
    }


    @Provides
    @Singleton
    fun provideNetworkConnection(@ApplicationContext context: Context) =
        NetworkConnection(provideConnectivityManager(context))


    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}