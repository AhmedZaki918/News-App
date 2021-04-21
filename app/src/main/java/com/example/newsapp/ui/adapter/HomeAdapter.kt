package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutHomeBinding
import com.example.newsapp.databinding.LayoutSecondBinding
import com.example.newsapp.data.local.Constants
import com.example.newsapp.util.Coroutines
import com.example.newsapp.util.OnAdapterClick
import com.example.newsapp.util.setupGlide
import javax.inject.Inject

@Suppress("SENSELESS_COMPARISON")
class HomeAdapter @Inject constructor(
    val onAdapterClick: OnAdapterClick,
    val articleDao: ArticleDao
) : PagingDataAdapter<Article, RecyclerView.ViewHolder>(Constants.USER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            ViewHolderTwo(
                LayoutSecondBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        } else ViewHolderOne(
            LayoutHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }


    // Get type of view
    override fun getItemViewType(position: Int): Int {
        val url = getItem(position)?.urlToImage
        if (url.isNullOrEmpty()) {
            return 0
        }
        return 1
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        // Layout without image
        currentItem?.urlToImage.apply {
            if (this.isNullOrEmpty()) {
                val holderTwo: ViewHolderTwo = holder as ViewHolderTwo
                holderTwo.bind(currentItem)
            } else {
                // Layout with image
                val holderOne: ViewHolderOne = holder as ViewHolderOne
                holderOne.bind(currentItem)
            }
        }
    }


    // First view holder
    inner class ViewHolderOne(private val binding: LayoutHomeBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.chSave.setOnClickListener(this)
        }

        // Bind data
        fun bind(article: Article?) {
            binding.apply {
                tvTitle.text = article?.title
                tvSource.text = article?.source?.name
                ivImage.setupGlide(article?.urlToImage,itemView)
                // Save the state of check box
                Coroutines.background {
                    val data = articleDao.fetchInArticles(article?.title)
                    chSave.isChecked = data != null
                }
            }
        }

        override fun onClick(v: View) {
            if (v.id == R.id.ch_save) {
                if (binding.chSave.isChecked) {
                    onAdapterClick.onItemClick(getItem(layoutPosition), Constants.SAVE)
                } else {
                    onAdapterClick.onItemClick(getItem(layoutPosition), Constants.REMOVE)
                }
            } else {
                onAdapterClick.onItemClick(getItem(layoutPosition))
            }
        }
    }


    // Second view holder
    inner class ViewHolderTwo(private val binding: LayoutSecondBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.chSave.setOnClickListener(this)
        }

        // Bind data
        fun bind(article: Article?) {
            binding.apply {
                tvTitle.text = article?.title
                tvSource.text = article?.source?.name
                // Save the state of check box
                Coroutines.background {
                    val data = articleDao.fetchInArticles(article?.title)
                    chSave.isChecked = data != null
                }
            }
        }

        override fun onClick(v: View) {
            if (v.id == R.id.ch_save) {
                if (binding.chSave.isChecked) {
                    onAdapterClick.onItemClick(getItem(layoutPosition), Constants.SAVE)
                } else {
                    onAdapterClick.onItemClick(getItem(layoutPosition), Constants.REMOVE)
                }
            } else {
                onAdapterClick.onItemClick(getItem(layoutPosition))
            }
        }
    }
}