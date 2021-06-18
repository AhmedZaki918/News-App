package com.example.newsapp.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutHomeBinding
import com.example.newsapp.util.OnAdapterClick
import com.example.newsapp.util.setupGlide
import javax.inject.Inject

class FirstViewHolder @Inject constructor(
    private val binding: LayoutHomeBinding,
    private val onAdapterClick: OnAdapterClick
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
        binding.ibShare.setOnClickListener(this)
    }

    private var currentPosition: Article? = null


    // Bind data
    fun bind(article: Article?) {
        binding.apply {
            tvTitle.text = article?.title
            tvSource.text = article?.source?.name
            // Setup glide
            ivImage.setupGlide(article?.urlToImage, itemView)
            currentPosition = article
        }
    }


    override fun onClick(v: View) {
        if (v.id == R.id.ib_share) {
            onAdapterClick.onItemClick(currentPosition, Constants.SHARE)
        } else {
            onAdapterClick.onItemClick(currentPosition)
        }
    }
}