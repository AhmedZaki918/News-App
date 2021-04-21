package com.example.newsapp.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserPreferences @Inject constructor(@ApplicationContext context: Context) {


    private var pref: SharedPreferences =
        context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    fun saveData(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun readData(key: String): Boolean {
        val status = pref.getString(key, "")
        return status == "true"
    }
}