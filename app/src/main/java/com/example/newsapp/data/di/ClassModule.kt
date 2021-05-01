package com.example.newsapp.data.di

import android.content.Context
import com.example.newsapp.data.local.UserPreferences
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
    fun provideUserPref(@ApplicationContext context: Context) = UserPreferences(context)
}