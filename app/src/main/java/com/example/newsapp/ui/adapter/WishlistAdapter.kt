package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutWishilstBinding
import com.example.newsapp.ui.adapter.viewholder.WishlistViewHolder
import com.example.newsapp.util.OnAdapterClick

class WishlistAdapter(
    private val onAdapterClick: OnAdapterClick,
) :
    PagingDataAdapter<Article, WishlistViewHolder>(Constants.USER_COMPARATOR) {

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        return WishlistViewHolder(
            LayoutWishilstBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            ), onAdapterClick
        )
    }

    // Bind view holder
    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}