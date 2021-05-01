package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentBaseBinding
import com.example.newsapp.ui.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass.
 */
class BaseFragment : Fragment() {

    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        _binding = FragmentBaseBinding.inflate(inflater, container, false)

        setupTabLayout()
        return binding.root
    }


    private fun setupTabLayout() {
        binding.apply {
            viewPager.adapter = PagerAdapter(requireActivity())
            viewPager.offscreenPageLimit = 4
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Top"
                    }
                    1 -> {
                        tab.text = "Health"
                    }
                    2 -> {
                        tab.text = "Tech"
                    }
                    3 -> {
                        tab.text = "Sports"
                    }
                }
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}