package com.example.newsapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.util.startActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment : Fragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userPreferences: UserPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Settings"
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        readData()
        initViews()
        return binding.root
    }


    override fun onClick(v: View) {
        if (v.id == R.id.cl_font_size)
            context?.startActivity(FontActivity::class.java)
    }


    override fun onResume() {
        super.onResume()
        readData()
    }


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        Constants.apply {
            if (isChecked) userPreferences.saveData(STATUS, TRUE)
            else if (!isChecked) userPreferences.saveData(STATUS, FALSE)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initViews() {
        binding.apply {
            // Save the state of switch button
            if (userPreferences.readData(Constants.STATUS)) switchButton.isChecked = true
            // Register listeners
            switchButton.setOnCheckedChangeListener(this@SettingFragment)
            clFontSize.setOnClickListener(this@SettingFragment)
        }
    }


    // Retrieve font size via shared preferences
    // to update current state of font size textView
    private fun readData() {
        binding.apply {
            Constants.apply {
                userPreferences.apply {
                    when {
                        readFontSize(FONT_SIZE) == FOURTEEN -> {
                            tvFont.text = SMALL
                        }
                        readFontSize(FONT_SIZE) == SIXTEEN -> {
                            tvFont.text = DEFAULT
                        }
                        readFontSize(FONT_SIZE) == EIGHTEEN -> {
                            tvFont.text = LARGE
                        }
                        readFontSize(FONT_SIZE) == TWENTY -> {
                            tvFont.text = EXTRA_LARGE
                        }
                        readFontSize(FONT_SIZE) == TWENTY_TWO -> {
                            tvFont.text = JUMBO
                        }
                    }
                }
            }
        }
    }
}