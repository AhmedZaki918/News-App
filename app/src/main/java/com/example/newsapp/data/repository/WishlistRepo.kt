package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.util.Coroutines
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishlistRepo @Inject constructor(
    private val articleDao: ArticleDao
) {
    // Initialization
    private lateinit var list: LiveData<List<Article>>


    // Receive data from database and update ui
    fun fetchArticles(): LiveData<List<Article>> {
        list = articleDao.getAllArticles()
        return list
    }


    // Delete one item from database
    fun deleteArticle(article: Article?) {
        Coroutines.background {
            if (article != null) {
                articleDao.deleteArticle(article)
            }
        }
    }

    // Delete all articles from database
    fun deleteAll() {
        Coroutines.background {
            articleDao.deleteAll()
        }
    }
}