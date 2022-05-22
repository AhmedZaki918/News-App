package com.example.newsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlesResponse(
    var articles: List<Article>?,
    val status: String? = "",
    val totalResults: Int? = 0

) : Parcelable