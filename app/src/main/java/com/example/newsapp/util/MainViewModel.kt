package com.example.newsapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.network.APIService
import com.example.newsapp.data.repository.ArticlesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: APIService
) :
    ViewModel() {

    fun getData(category: String = "") =
        Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
            ArticlesRepo(api, category)
        }.flow.cachedIn(viewModelScope)
}