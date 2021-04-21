package com.example.newsapp.data.repository

import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.local.Constants
import com.example.newsapp.util.Coroutines
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepo @Inject constructor(val articleDao: ArticleDao) {


    // Add an article or remove it from database
    fun addOrRemove(article: Article?, operation: String = "") {
        Coroutines.background {
            if (operation == Constants.SAVE) articleDao.addArticle(article!!)
            else
                articleDao.deleteArticle(article!!)
        }
    }
}