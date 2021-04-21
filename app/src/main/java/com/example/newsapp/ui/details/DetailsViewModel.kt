package com.example.newsapp.ui.details

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.DetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: DetailsRepo) : ViewModel() {

    // Send request to repository to save or remove data
    fun createOperation(article: Article?, operation: String = "") {
        repo.addOrRemove(article, operation)
    }
}