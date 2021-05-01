package com.example.newsapp.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.local.Constants
import com.example.newsapp.ui.adapter.WishlistAdapter
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentWishlistBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.*
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class WishlistFragment : Fragment(), OnAdapterClick, View.OnClickListener {

    // Initialization
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private var wishlistAdapter: WishlistAdapter? = null
    private var viewModel: WishlistViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Saved"
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(WishlistViewModel::class.java)
        viewModel?.sendRequest()?.observe(viewLifecycleOwner, {
            updateUi(it)
        })

        binding.floatingButton.setOnClickListener(this)
        return binding.root
    }


    override fun onClick(v: View) {
        if (v.id == R.id.floating_button) {
            // Open alert dialog
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle(R.string.deleteAll)
                .setMessage(R.string.deleteMess)
                .setPositiveButton((R.string.delete)) { _, _ ->
                    viewModel?.deleteAll()
                }
            builder.create().show()
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
        _binding = null
    }


    // Update the list of articles
    private fun updateUi(list: List<Article>) {
        if (list.isNotEmpty()) {
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            wishlistAdapter = WishlistAdapter(this@WishlistFragment, list)
            binding.recyclerView.adapter = wishlistAdapter
        } else {
            // Display no content saved if the list is empty
            binding.apply {
                recyclerView.visibility = GONE
                tvNoContent.visibility = VISIBLE
                tvDescription.visibility = VISIBLE
                ivNoArticles.visibility = VISIBLE
                floatingButton.visibility = GONE
            }
        }
    }
}