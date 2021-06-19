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
class FontActivity @Inject constructor() : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {


    @Inject
    lateinit var userPreferences: UserPreferences
    private lateinit var binding: ActivityFontBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsApp)
        binding = ActivityFontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from shared preferences
        binding.apply {
            FontPreferences(userPreferences).apply {
                readData(tvFontSize, tvPreview, seekBar)
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(this)
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


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        FontPreferences(userPreferences).apply {
            editFontSize(progress, binding.tvFontSize, binding.tvPreview)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}