package com.example.newsapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ActivityDetailsBinding
import com.example.newsapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity(), View.OnClickListener {

    // Initialization
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var article: Article
    private lateinit var viewModel: DetailsViewModel

    @Inject
    lateinit var articleDao: ArticleDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsApp)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        saveState()
        updateUi()
        binding.tvReadMore.setOnClickListener(this)
        binding.cbSave.setOnClickListener(this)
    }


    private fun initViews() {
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        val data = intent
        article = data.getParcelableExtra(Constants.MODEL)!!
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_read_more -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                startActivity(intent)
            }
            R.id.cb_save -> {
                if (binding.cbSave.isChecked) {
                    viewModel.createOperation(article, Constants.SAVE)
                    toast(R.string.saved)
                } else {
                    viewModel.createOperation(article)
                    toast(R.string.removed)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.share -> {
                share(article)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateUi() {
        // Display image
        binding.apply {
            if (article.urlToImage == null) {
                imageView.visibility = View.GONE
            } else {
                imageView.setupGlide(
                    article.urlToImage,
                    this@DetailsActivity.applicationContext
                )
            }
            // Check nullable from api
            article.apply {
                tvContent.checkNull(content)
                tvDescription.checkNull(description)
                tvDate.checkNull(publishedAt)
                if (tvAuthor.checkNull(author)) {
                    tvBy.visibility = View.GONE
                }
                tvTitle.checkNull(title)
                tvSource.checkNull(source?.name)
            }
        }
    }


    // Save the state of check box
    @Suppress("SENSELESS_COMPARISON")
    private fun saveState() {
        Coroutines.background {
            val data = articleDao.fetchInArticles(article.title)
            binding.cbSave.isChecked = data != null
        }
    }
}