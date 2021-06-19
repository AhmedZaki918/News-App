package com.example.newsapp.data.repository

import android.widget.TextView
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import com.example.newsapp.data.model.Article
import com.example.newsapp.util.Coroutines
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepo @Inject constructor(
    val articleDao: ArticleDao,
    val userPreferences: UserPreferences
) {


    // Add an article or remove it from database
    fun addOrRemove(article: Article?, operation: String = "") {
        Coroutines.background {
            if (operation == Constants.SAVE) articleDao.addArticle(article!!)
            else
                articleDao.deleteArticle(article!!)
        }
    }


    fun readData(description: TextView, content: TextView) {
        Constants.apply {
            userPreferences.apply {
                when {
                    readFontSize(FONT_SIZE) == FOURTEEN -> {
                        description.textSize = 14f
                        content.textSize = 14f
                    }
                    readFontSize(FONT_SIZE) == SIXTEEN -> {
                        description.textSize = 16f
                        content.textSize = 16f
                    }
                    readFontSize(FONT_SIZE) == EIGHTEEN -> {
                        description.textSize = 18f
                        content.textSize = 18f
                    }
                    readFontSize(FONT_SIZE) == TWENTY -> {
                        description.textSize = 20f
                        content.textSize = 20f
                    }
                    readFontSize(FONT_SIZE) == TWENTY_TWO -> {
                        description.textSize = 22f
                        content.textSize = 22f
                    }
                }
            }
        }
    }
}