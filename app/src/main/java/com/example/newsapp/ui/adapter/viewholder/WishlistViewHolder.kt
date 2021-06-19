package com.example.newsapp.ui.adapter.viewholder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutWishilstBinding
import com.example.newsapp.util.OnAdapterClick
import com.example.newsapp.util.setupGlide


class WishlistViewHolder(
    private val binding: LayoutWishilstBinding,
    private val onAdapterClick: OnAdapterClick
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        binding.ibDelete.setOnClickListener(this)
        binding.ibShare.setOnClickListener(this)
        itemView.setOnClickListener(this)
    }

    private var currentPosition: Article? = null


    // Bind data
    fun bind(article: Article) {
        binding.apply {
            tvTitle.text = article.title
            tvDate.text = article.publishedAt?.substring(0, 10)

            // if the image is null
            article.urlToImage.apply {
                if (this.isNullOrEmpty()) {
                    ivArticle.visibility = GONE
                    ivNoImage.visibility = VISIBLE
                    ivNoImage.setImageResource(R.drawable.no_image)
                    // Image is not null
                } else ivArticle.setupGlide(this, itemView)
            }
            currentPosition = article
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_delete -> {
                onAdapterClick.onItemClick(currentPosition, Constants.DELETE)
            }
            R.id.ib_share -> {
                onAdapterClick.onItemClick(currentPosition, Constants.SHARE)
            }
            else -> {
                onAdapterClick.onItemClick(currentPosition)
            }
        }
    }
}