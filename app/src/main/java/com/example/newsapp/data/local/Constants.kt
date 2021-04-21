package com.example.newsapp.data.local

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.data.model.Article

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "Your Api Key"
    const val PAGE_SIZE = 20
    const val STARTING_PAGE_INDEX = 1
    const val MODEL = "ARTICLE_MODEL"
    const val LANGUAGE = "en"
    const val TECH = "technology"
    const val SPORT = "sports"
    const val HEALTH = "health"
    const val ALL = "ALL_CATEGORY"
    const val SAVE = "SAVE"
    const val REMOVE = "REMOVE"
    const val MyPREFERENCES = "MyPrefs"
    const val STATUS = "status"
    const val TRUE = "true"
    const val FALSE = "false"


    // For paging library
    val USER_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.publishedAt == newItem.publishedAt

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}