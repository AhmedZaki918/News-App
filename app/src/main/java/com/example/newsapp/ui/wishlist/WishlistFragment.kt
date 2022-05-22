package com.example.newsapp.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentWishlistBinding
import com.example.newsapp.ui.adapter.WishlistAdapter
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


/**
 * A simple [Fragment] subclass.
 */


@AndroidEntryPoint
class WishlistFragment : Fragment(), OnAdapterClick, View.OnClickListener {

    // Initialization
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var wishlistAdapter: WishlistAdapter
    private var viewModel: WishlistViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Saved"
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        initViews()
        handleLoadState()
        Coroutines.main {
            viewModel?.sendRequest()?.collectLatest {
                wishlistAdapter.submitData(it)
            }
        }
        return binding.root
    }


    override fun onClick(v: View) {
        if (v.id == R.id.floating_button) {
            // Open alert dialog
            alert(R.string.deleteAll, R.string.deleteMess) {
                positiveButton(R.string.delete) {
                    viewModel?.deleteAll()
                }
                negativeButton(R.string.cancel)
            }.show()
        }
    }


    override fun onItemClick(article: Article?, operation: String) {
        Constants.apply {
            when (operation) {
                DELETE -> {
                    viewModel?.sendDeleteRequest(article)
                }
                SHARE -> {
                    if (article != null) {
                        requireActivity().share(article)
                    } else requireActivity().toast(R.string.missingUrl)
                }
                else ->
                    context?.startActivity(article!!, DetailsActivity::class.java)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (_binding == null) _binding = null
    }


    private fun initViews() {
        wishlistAdapter = WishlistAdapter(this)
        binding.recyclerView.adapter = wishlistAdapter
        viewModel = ViewModelProvider(this)[WishlistViewModel::class.java]
        binding.floatingButton.setOnClickListener(this)
    }


    private fun handleLoadState() {
        wishlistAdapter.addLoadStateListener {
            if (it.append.endOfPaginationReached) {
                if (wishlistAdapter.itemCount < 1) {
                    binding.apply {
                        floatingButton.visibility = GONE
                        recyclerView.visibility = GONE
                        tvNoContent.visibility = VISIBLE
                        tvDescription.visibility = VISIBLE
                        ivNoArticles.visibility = VISIBLE
                    }
                }
            }
        }
    }
}