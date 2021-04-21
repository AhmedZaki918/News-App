package com.example.newsapp.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
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

        initViews()
        return binding.root
    }


    override fun onClick(v: View) {
        if (v.id == R.id.notification_layout) {
            openNotificationSettings()
        }
    }


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        Constants.apply {
            if (isChecked) {
                userPreferences.saveData(STATUS, TRUE)
            } else if (!isChecked) {
                userPreferences.saveData(STATUS, FALSE)
            }
        }
    }


    private fun initViews() {
        binding.apply {
            // Save the state of switch button
            if (userPreferences.readData(Constants.STATUS)) {
                switchButton.isChecked = true
            }
            notificationLayout.setOnClickListener(this@SettingFragment)
            switchButton.setOnCheckedChangeListener(this@SettingFragment)
        }
    }


    private fun openNotificationSettings() {
        Intent().apply {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, requireActivity().packageName)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    putExtra("app_package", requireActivity().packageName)
                    putExtra("app_uid", requireActivity().applicationInfo.uid)
                }
                else -> {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    addCategory(Intent.CATEGORY_DEFAULT)
                    data = Uri.parse("package:" + requireActivity().packageName)
                }
            }
            requireActivity().startActivity(this)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}