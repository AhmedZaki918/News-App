package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutHomeBinding
import com.example.newsapp.databinding.LayoutSecondBinding
import com.example.newsapp.ui.adapter.viewholder.FirstViewHolder
import com.example.newsapp.ui.adapter.viewholder.SecondViewHolder
import com.example.newsapp.util.OnAdapterClick
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    private val onAdapterClick: OnAdapterClick
) : PagingDataAdapter<Article, RecyclerView.ViewHolder>(Constants.USER_COMPARATOR) {


    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            SecondViewHolder(
                LayoutSecondBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                ), onAdapterClick
            )
        } else FirstViewHolder(
            LayoutHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            ), onAdapterClick
        )
    }


    // Get type of view
    override fun getItemViewType(position: Int): Int {
        val url = getItem(position)?.urlToImage
        return if (url.isNullOrEmpty()) 0 else 1
    }


    // Bind view holder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Layout without image
        getItem(position)?.urlToImage.apply {
            if (this.isNullOrEmpty()) {
                val holderTwo: SecondViewHolder = holder as SecondViewHolder
                holderTwo.bind(getItem(position))
                // Layout with image
            } else {
                val holderOne: FirstViewHolder = holder as FirstViewHolder
                holderOne.bind(getItem(position))
            }
        }
    }
}