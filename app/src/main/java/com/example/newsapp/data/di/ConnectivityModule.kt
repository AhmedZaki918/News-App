package com.example.newsapp.data.di

import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import com.example.newsapp.R
import com.example.newsapp.util.NetworkConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {


    @Provides
    @Singleton
    fun provideNetworkConnection(@ApplicationContext context: Context) =
        NetworkConnection(provideConnectivityManager(context))


    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


    @Provides
    fun provideMediaPlayer(@ApplicationContext context: Context): MediaPlayer {
        return MediaPlayer.create(context, R.raw.intro)
    }
}