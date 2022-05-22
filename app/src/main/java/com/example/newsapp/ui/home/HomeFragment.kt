package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.adapter.HomeAdapter
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class HomeFragment @Inject constructor(private val category: String) :
    Fragment(),
    OnAdapterClick,
    View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    // Initialization
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var networkConnection: NetworkConnection


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initViews()
        handleLoadState()
        Coroutines.main {
            viewModel.getData(category).collectLatest { pagedData ->
                homeAdapter.submitData(pagedData)
            }
        }
        return binding.root
    }


    private fun initViews() {
        homeAdapter = HomeAdapter(this)
        binding.recyclerView.adapter = homeAdapter
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.btnRetry.setOnClickListener(this)
        binding.switchRefresh.setOnRefreshListener(this)
    }


    // Handle load state from paging
    private fun handleLoadState() {
        binding.apply {
            homeAdapter.addLoadStateListener {
                when (it.refresh) {
                    LoadState.Loading -> {
                        loadingIndicator.show()
                    }
                    is LoadState.NotLoading -> {
                        loadingIndicator.hide()
                    }
                    is LoadState.Error -> {
                        loadingIndicator.hide()
                        btnRetry.visibility = VISIBLE
                        requireActivity().toast((it.refresh as LoadState.Error).error.message)
                    }
                }
            }
        }
    }


    override fun onItemClick(article: Article?, operation: String) {
        Constants.apply {
            when (operation) {
                SHARE -> {
                    requireContext().share(article)
                }
                else -> {
                    context?.startActivity(article, DetailsActivity::class.java)
                }
            }
        }
    }


    override fun onClick(v: View) {
        if (v.id == R.id.btn_retry) {
            binding.btnRetry.visibility = GONE
            homeAdapter.retry()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onRefresh() {
        if (networkConnection.isConnected) {
            homeAdapter.retry()
            homeAdapter.notifyDataSetChanged()
        } else requireContext().toast(R.string.connection_lost)
        binding.switchRefresh.isRefreshing = false
    }
}