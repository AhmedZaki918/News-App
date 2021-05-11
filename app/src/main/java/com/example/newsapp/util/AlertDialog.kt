package com.example.newsapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.DialogInfoBinding


inline fun Activity.alert(
    titleResource: Int = 0,
    messageResource: Int = 0,
    func: AlertDialogHelper.() -> Unit
): AlertDialog {
    val title = if (titleResource == 0) null else getString(titleResource)
    val message = if (messageResource == 0) null else getString(messageResource)
    return AlertDialogHelper(this, title, message).apply {
        func()
    }.create()
}


inline fun Fragment.alert(
    titleResource: Int = 0,
    messageResource: Int = 0,
    func: AlertDialogHelper.() -> Unit
): AlertDialog {
    val title = if (titleResource == 0) null else getString(titleResource)
    val message = if (messageResource == 0) null else getString(messageResource)
    return AlertDialogHelper(this.requireContext(), title, message).apply {
        func()
    }.create()
}


@SuppressLint("InflateParams")
class AlertDialogHelper(context: Context, title: CharSequence?, message: CharSequence?) {

    private val binding: DialogInfoBinding by lazyFast {
        DialogInfoBinding.inflate(LayoutInflater.from(context))
    }

    private val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        .setView(binding.root)

    private var dialog: AlertDialog? = null
    private var cancelable: Boolean = true

    init {
        binding.dialogInfoTitleTextView.text = title
        binding.dialogInfoMessageTextView.text = message
    }


    fun positiveButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        with(binding.dialogInfoPositiveButton) {
            text = builder.context.getString(textResource)
            setClickListenerToDialogButton(func)
        }
    }


    fun negativeButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        with(binding.dialogInfoNegativeButton) {
            text = builder.context.getString(textResource)
            setClickListenerToDialogButton(func)
        }
    }


    fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()
        return dialog!!
    }


    private fun Button.setClickListenerToDialogButton(func: (() -> Unit)?) {
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }
    }
}

/**
 * Implementation of lazy that is not thread safe. Useful when you know what thread you will be
 * executing on and are not worried about synchronization.
 */
fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}