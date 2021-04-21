package com.example.newsapp.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article


fun Context.toast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun ProgressBar.hide() {
    visibility = View.INVISIBLE
}


fun ProgressBar.show() {
    visibility = View.VISIBLE
}


fun <T> Context.startActivity(article: Article?, cls: Class<T>) {
    val intent = Intent(this, cls)
    intent.putExtra(Constants.MODEL, article)
    this.startActivity(intent)
}


fun TextView.checkNull(content: String?): Boolean {
    if (content.isNullOrEmpty()) {
        this.visibility = View.GONE
        return true
    } else
        this.text = content
    return false
}


fun ImageView.setupGlide(url: String?, view: View) {
    Glide.with(view)
        .load(url)
        .placeholder(R.drawable.square)
        .into(this)
}


fun ImageView.setupGlide(url: String?, context: Context) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.square)
        .into(this)
}


fun Context.share(article: Article?) {
    val intent = Intent()
    intent.apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, article?.url)
        type = "text/plain"
        startActivity(this)
    }
}


fun switchVisibility(first: View, second: View) {
    first.visibility = View.GONE
    second.visibility = View.VISIBLE
}