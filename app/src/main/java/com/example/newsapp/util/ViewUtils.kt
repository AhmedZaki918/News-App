package com.example.newsapp.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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


//inline fun Fragment.alert(
//    titleResource: Int = 0,
//    messageResource: Int = 0,
//    noinline action: () -> Unit,
//    func: AlertDialogHelper.() -> Unit
//) {
//    val title = if (titleResource == 0) null else getString(titleResource)
//    val message = if (messageResource == 0) null else getString(messageResource)
//    AlertDialogHelper(this.requireContext(), title, message,action).apply {
//        func()
//    }.builder
//}
//
//class AlertDialogHelper(var context: Context, title: CharSequence?, message: CharSequence?, action: () -> Unit) {
//
//    val builder = AlertDialog.Builder(context).apply {
//        setTitle(title)
//            .setMessage(message)
//            .setPositiveButton((R.string.deleteMess)) { _, _ ->
//            }.create().show()
//    }
//}
