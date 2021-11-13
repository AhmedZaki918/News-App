package com.example.newsapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =


        Room.databaseBuilder(app, ArticleDatabase::class.java, "article_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(db: ArticleDatabase) = db.getArticleDao()
}