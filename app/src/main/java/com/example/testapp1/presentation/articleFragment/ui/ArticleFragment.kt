package com.example.testapp1.presentation.articleFragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.testapp1.NewsApplication
import com.example.testapp1.R
import com.example.testapp1.databinding.FragmentArticleBinding
import com.example.testapp1.di.ViewModelFactory
import com.example.testapp1.presentation.articleFragment.presentation.ArticleFragmentViewModel
import com.example.testapp1.utils.BaseClasses.BaseFragment
import com.example.testapp1.utils.visibilityIf
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ArticleFragment : BaseFragment<FragmentArticleBinding>(FragmentArticleBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ArticleFragmentViewModel by viewModels {
        viewModelFactory
    }
    private val args: ArticleFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        val component = (requireActivity().application as NewsApplication).component
        component.inject(this)
        super.onAttach(context)
    }

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
            binding.fab.visibilityIf(true)
            binding.fab.setOnClickListener {
                viewModel.save(articleRemote)
                Snackbar.make(view, getString(R.string.article_saved_successfully), Snackbar.LENGTH_SHORT).show()
            }
        } else {
            binding.fab.visibilityIf(false)
        }
    }
}