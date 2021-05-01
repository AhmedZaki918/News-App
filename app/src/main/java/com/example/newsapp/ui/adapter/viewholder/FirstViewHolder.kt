package com.example.newsapp.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutHomeBinding
import com.example.newsapp.util.Coroutines
import com.example.newsapp.util.OnAdapterClick
import com.example.newsapp.util.setupGlide
import javax.inject.Inject

class FirstViewHolder @Inject constructor(
    private val binding: LayoutHomeBinding,
    private val onAdapterClick: OnAdapterClick,
    val articleDao: ArticleDao
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
        binding.chSave.setOnClickListener(this)
    }

    private var currentPosition: Article? = null


    // Bind data
    fun bind(article: Article?) {
        binding.apply {
            tvTitle.text = article?.title
            tvSource.text = article?.source?.name
            ivImage.setupGlide(article?.urlToImage, itemView)
            // Save the state of check box
            Coroutines.background {
                @Suppress("SENSELESS_COMPARISON")
                chSave.isChecked = articleDao.fetchInArticles(article?.title) != null
            }
            currentPosition = article
        }
    }


    override fun onClick(v: View) {
        if (v.id == R.id.ch_save) {
            if (binding.chSave.isChecked) {
                onAdapterClick.onItemClick(currentPosition, Constants.SAVE)
            } else {
                onAdapterClick.onItemClick(currentPosition, Constants.REMOVE)
            }
        } else {
            onAdapterClick.onItemClick(currentPosition)
        }
    }
}