package com.example.testapp1.feature.articleFragment.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.testapp1.R
import com.example.testapp1.feature.articleFragment.presentation.ArticleFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*
import javax.inject.Inject

class ArticleFragment : Fragment(R.layout.fragment_article) {

    @Inject
    lateinit var viewModel: ArticleFragmentViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.selectedNews
        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(article.url) }
        }

        fab.setOnClickListener {
            viewModel.save(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}