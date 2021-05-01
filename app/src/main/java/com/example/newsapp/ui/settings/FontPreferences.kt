package com.example.newsapp.ui.settings

import android.widget.SeekBar
import android.widget.TextView
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import javax.inject.Inject

class FontPreferences @Inject constructor(private val userPreferences: UserPreferences) {


    // Retrieve data from shared preferences
    fun readData(tvFontSize: TextView, tvPreview: TextView, seekBar: SeekBar) {
        Constants.apply {
            userPreferences.apply {
                when {
                    readFontSize(FONT_SIZE) == FOURTEEN -> {
                        tvFontSize.text = SMALL
                        tvPreview.textSize = 14f
                        seekBar.progress = 0
                    }
                    readFontSize(FONT_SIZE) == SIXTEEN -> {
                        tvFontSize.text = DEFAULT
                        tvPreview.textSize = 16f
                        seekBar.progress = 1
                    }
                    readFontSize(FONT_SIZE) == EIGHTEEN -> {
                        tvFontSize.text = LARGE
                        tvPreview.textSize = 18f
                        seekBar.progress = 2
                    }
                    readFontSize(FONT_SIZE) == TWENTY -> {
                        tvFontSize.text = EXTRA_LARGE
                        tvPreview.textSize = 20f
                        seekBar.progress = 3
                    }
                    readFontSize(FONT_SIZE) == TWENTY_TWO -> {
                        tvFontSize.text = JUMBO
                        tvPreview.textSize = 22f
                        seekBar.progress = 4
                    }
                }
            }
        }
    }


    // Save and edit state of views in shared preferences
    fun editFontSize(progress: Int, fontSize: TextView, preview: TextView) {
        Constants.apply {
            userPreferences.apply {
                when (progress) {
                    0 -> {
                        fontSize.text = SMALL
                        preview.textSize = 14f
                        saveData(FONT_SIZE, FOURTEEN)
                    }
                    1 -> {
                        fontSize.text = DEFAULT
                        preview.textSize = 16f
                        saveData(FONT_SIZE, SIXTEEN)
                    }
                    2 -> {
                        fontSize.text = LARGE
                        preview.textSize = 18f
                        saveData(FONT_SIZE, EIGHTEEN)
                    }
                    3 -> {
                        fontSize.text = EXTRA_LARGE
                        preview.textSize = 20f
                        saveData(FONT_SIZE, TWENTY)
                    }
                    4 -> {
                        fontSize.text = JUMBO
                        preview.textSize = 22f
                        saveData(FONT_SIZE, TWENTY_TWO)
                    }
                }
            }
        }
    }
}