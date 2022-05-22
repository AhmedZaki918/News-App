package com.example.newsapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "articles")
@Parcelize
data class Article(
    @PrimaryKey
    val title: String,
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: Source?,
    val author: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
) : Parcelable