package com.example.newsapp.ui.wishlist

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.WishlistRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repo: WishlistRepo
) : ViewModel() {

    // Send request to repository to display data
    fun sendRequest() =
        repo.fetchArticles()

    // Send delete request from database
    fun sendDeleteRequest(article: Article?) {
        repo.deleteArticle(article)
    }

    fun deleteAll() {
        repo.deleteAll()
    }
}