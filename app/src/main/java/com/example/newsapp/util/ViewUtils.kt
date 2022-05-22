package com.example.newsapp.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article


fun <T> Context.toast(message: T) {
    if (message is String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    } else if (message is Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


fun ProgressBar.hide() {
    visibility = View.INVISIBLE
}


fun ProgressBar.show() {
    visibility = View.VISIBLE
}


fun <T> Context.startActivity(article: Article?, cls: Class<T>) {
    Intent(this, cls).apply {
        putExtra(Constants.MODEL, article)
        startActivity(this)
    }
}


fun <T> Context.startActivity(cls: Class<T>) {
    Intent(this, cls).apply {
        startActivity(this)
    }
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
    Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, article?.url)
        type = Constants.TYPE_SHARE
        startActivity(this)
    }
}


fun FragmentManager.createFragment(fragment: Fragment, frameLayout: Int) =
    this.beginTransaction().apply {
        replace(frameLayout, fragment)
        commit()
    }
