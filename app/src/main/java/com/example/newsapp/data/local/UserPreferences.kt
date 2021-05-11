package com.example.newsapp.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private var pref: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {

    fun saveData(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun readData(key: String): Boolean {
        val status = pref.getString(key, "")
        return status == Constants.TRUE
    }

    fun readFontSize(key: String): String? {
        return pref.getString(key, "")
    }
}