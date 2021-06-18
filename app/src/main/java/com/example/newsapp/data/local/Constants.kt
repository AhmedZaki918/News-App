package com.example.newsapp.data.local

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.data.model.Article

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "ca9c1cab47bb435ca4288f360849c3d2"
    const val PAGE_SIZE = 20
    const val STARTING_PAGE_INDEX = 1
    const val MODEL = "ARTICLE_MODEL"
    const val LANGUAGE = "en"
    const val TECH = "technology"
    const val SPORT = "sports"
    const val HEALTH = "health"
    const val ALL = "ALL_CATEGORY"
    const val SAVE = "SHARE"
    const val DELETE = "delete"
    const val SHARE = "share"
    const val TYPE_SHARE = "text/plain"

    // Shared Preferences
    const val MyPREFERENCES = "MyPrefs"
    const val STATUS = "status"
    const val TRUE = "true"
    const val FALSE = "false"

    // Font size
    const val SMALL = "Small"
    const val DEFAULT = "Default"
    const val LARGE = "Large"
    const val EXTRA_LARGE = "Extra Large"
    const val JUMBO = "Jumbo"
    const val FONT_SIZE = "font_size"
    const val FOURTEEN = "14"
    const val SIXTEEN = "16"
    const val EIGHTEEN = "18"
    const val TWENTY = "20"
    const val TWENTY_TWO = "22"


    // For paging library
    val USER_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.publishedAt == newItem.publishedAt

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}