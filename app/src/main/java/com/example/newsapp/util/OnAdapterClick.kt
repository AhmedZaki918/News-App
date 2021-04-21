package com.example.newsapp.util

import com.example.newsapp.data.model.Article

interface OnAdapterClick {
    fun onItemClick(article: Article?, operation: String = "")
}