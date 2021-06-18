package com.example.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.ArticlesResponse
import com.example.newsapp.data.network.APIService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesRepo @Inject constructor(
    private val api: APIService,
    private val category: String
) : PagingSource<Int, Article>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: Constants.STARTING_PAGE_INDEX
            val response = createRequest(position)
            val nextKey = if (response.articles!!.isEmpty()) {
                null
            } else {
                // Ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / Constants.PAGE_SIZE)
            }
            LoadResult.Page(
                data = response.articles!!,
                prevKey = if (position == Constants.STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int {
        return 0
    }


    // Check category type and return required api response
    private suspend fun createRequest(page: Int): ArticlesResponse {
        Constants.apply {
            return when (category) {
                ALL -> {
                    api.getAll(API_KEY, LANGUAGE, page)
                }
                HEALTH -> {
                    api.getCategory(API_KEY, LANGUAGE, HEALTH, page)
                }
                TECH -> {
                    api.getCategory(API_KEY, LANGUAGE, TECH, page)
                }
                else -> {
                    api.getCategory(API_KEY, LANGUAGE, SPORT, page)
                }
            }
        }
    }
}