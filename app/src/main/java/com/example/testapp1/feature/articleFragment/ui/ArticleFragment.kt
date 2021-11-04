package com.example.testapp1.feature.articleFragment.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.testapp1.databinding.FragmentArticleBinding
import com.example.testapp1.feature.articleFragment.presentation.ArticleFragmentViewModel
import com.example.testapp1.utils.BaseClasses.BaseFragment
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ArticleFragment : BaseFragment<FragmentArticleBinding>(FragmentArticleBinding::inflate) {

    @Inject
    lateinit var viewModel: ArticleFragmentViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleRemote = args.selectedNews
        val savedArticle = args.selectedSavedNews
        binding.webView.apply {
            webViewClient = WebViewClient()
            articleRemote?.let {
                it.url?.let { loadUrl(articleRemote.url!!) }
            }
            savedArticle?.let {
                it.articleInfo.url.let { loadUrl(savedArticle.articleInfo.url!!) }
            }
        }

        if (articleRemote != null) {
            binding.fab.visibility = View.VISIBLE
            binding.fab.setOnClickListener {
                viewModel.save(articleRemote)
                Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            binding.fab.visibility = View.GONE
        }
    }
}