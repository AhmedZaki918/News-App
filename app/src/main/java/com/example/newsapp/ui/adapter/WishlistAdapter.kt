package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutWishilstBinding
import com.example.newsapp.ui.adapter.viewholder.WishlistViewHolder
import com.example.newsapp.util.OnAdapterClick

class WishlistAdapter(
    private val onAdapterClick: OnAdapterClick,
    private var list: List<Article>
) :
    RecyclerView.Adapter<WishlistViewHolder>() {


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
        // Get current position
        holder.bind(list[position])
    }

    // Return the size of list
    override fun getItemCount(): Int {
        return list.size
    }
}