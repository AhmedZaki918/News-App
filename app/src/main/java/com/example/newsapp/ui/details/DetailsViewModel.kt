package com.example.newsapp.ui.details

import android.view.View
import android.widget.TextView
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

    // Retrieve saved font size
    fun retrieveFont(description: TextView, content: TextView) {
        repo.readData(description, content)
    }


    fun checkNull(view: TextView, content: String?): Boolean {
        if (content.isNullOrEmpty()) {
            view.visibility = View.GONE
            return true
        } else
            view.text = content
        return false
    }
}