package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutWishilstBinding
import com.example.newsapp.util.OnAdapterClick
import com.example.newsapp.util.setupGlide
import com.example.newsapp.util.switchVisibility

class WishlistAdapter(
    val onAdapterClick: OnAdapterClick,
    var list: List<Article>
) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {


    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutWishilstBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }


    // Bind view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get current position
        val currentItem = list[position]
        holder.bind(currentItem)
    }


    // Return the size of list
    override fun getItemCount(): Int {
        return list.size
    }


    // View holder class
    inner class ViewHolder(private val binding: LayoutWishilstBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.apply {
                ibDelete.setOnClickListener(this@ViewHolder)
                ibShare.setOnClickListener(this@ViewHolder)
                itemView.setOnClickListener(this@ViewHolder)
            }
        }

        // Bind data
        fun bind(article: Article) {
            binding.apply {
                tvTitle.text = article.title
                val date = article.publishedAt?.substring(0, 10)
                tvDate.text = date
                // Check if the image is null
                article.urlToImage.apply {
                    if (this.isNullOrEmpty()) {
                        switchVisibility(ivArticle, ivNoImage)
                        ivNoImage.setImageResource(R.drawable.no_image)
                    } else ivArticle.setupGlide(this, itemView)
                }
            }
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.ib_delete -> {
                    onAdapterClick.onItemClick(list[layoutPosition], "delete")
                }
                R.id.ib_share -> {
                    onAdapterClick.onItemClick(list[layoutPosition], "share")
                }
                else -> {
                    onAdapterClick.onItemClick(list[layoutPosition])
                }
            }
        }
    }
}