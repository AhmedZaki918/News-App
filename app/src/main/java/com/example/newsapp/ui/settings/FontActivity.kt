package com.example.newsapp.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.data.local.UserPreferences
import com.example.newsapp.databinding.ActivityFontBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FontActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFontBinding

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsApp)
        binding = ActivityFontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from shared preferences
        readData()
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?, progress: Int, fromUser: Boolean
            ) {
                FontPreferences(userPreferences).apply {
                    editFontSize(progress, binding.tvFontSize, binding.tvPreview)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun readData() {
        binding.apply {
            FontPreferences(userPreferences).apply {
                readData(tvFontSize, tvPreview, seekBar)
            }
        }
    }
}