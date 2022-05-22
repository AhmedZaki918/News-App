package com.example.newsapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.Constants
import com.example.newsapp.data.local.UserPreferences
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ActivityDetailsBinding
import com.example.newsapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
@Suppress("SENSELESS_COMPARISON")
class DetailsActivity : AppCompatActivity(), View.OnClickListener {

    // Initialization
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var article: Article
    private lateinit var viewModel: DetailsViewModel

    @Inject
    lateinit var articleDao: ArticleDao

    @Inject
    lateinit var userPreferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsApp)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        // Save the state of check box
        Coroutines.background {
            binding.cbSave.isChecked = articleDao.fetchInArticles(article.title) != null
        }
        updateUi()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_read_more -> {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(article.url)
                    startActivity(this)
                }
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


    private fun initViews() {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        // Retrieve font size saved in shared preferences
        viewModel.retrieveFont(binding.tvDescription, binding.tvContent)
        // Display article details via parcelable
        article = intent.getParcelableExtra(Constants.MODEL)!!
        binding.tvReadMore.setOnClickListener(this)
        binding.cbSave.setOnClickListener(this)
    }


    private fun updateUi() {
        // Display image
        binding.apply {
            if (article.urlToImage == null) imageView.visibility = GONE
            else {
                imageView.setupGlide(
                    article.urlToImage,
                    this@DetailsActivity.applicationContext
                )
            }
            // Check nullable from api
            viewModel.apply {
                article.apply {
                    checkNull(tvContent, content)
                    checkNull(tvDescription, description)
                    checkNull(tvDate, publishedAt)
                    checkNull(tvTitle, title)
                    checkNull(tvSource, source?.name)
                    if (checkNull(tvAuthor, author)) tvBy.visibility = GONE
                }
            }
        }
    }
}