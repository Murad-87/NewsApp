package com.example.testapp1.feature.searchNewsFragment.ui

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.databinding.FragmentSearchNewsBinding
import com.example.testapp1.feature.searchNewsFragment.presentation.SearchNewsViewModel
import com.example.testapp1.feature.ui.NewsAdapter
import com.example.testapp1.utils.BaseClasses.BaseFragment
import com.example.testapp1.utils.Constants
import com.example.testapp1.utils.Resource
import com.example.testapp1.utils.hasInternetConnection
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.android.synthetic.main.fragment_search_news.paginationProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchNewsFragment :
    BaseFragment<FragmentSearchNewsBinding>(FragmentSearchNewsBinding::inflate) {

    @Inject
    lateinit var viewModel: SearchNewsViewModel
    private val newsAdapter by lazy { NewsAdapter() }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            navigate(it)
        }

        delayedNewsSearch()

        viewModel.searchNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    handleSuccess(response)
                }
                is Resource.Error -> {
                    handleError(response)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun delayedNewsSearch() {
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getSearchNewsCall(
                            editable.toString(),
                            requireContext().hasInternetConnection()
                        )
                    }
                }
            }
        }
    }

    private fun handleSuccess(response: Resource<NewsResponse>) {
        hideProgressBar()
        response.data?.let { newsResponse ->
            newsAdapter.submitList(newsResponse.articles.toList())
            val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
            isLastPage = viewModel.searchNewsPage == totalPages
            if (isLastPage) {
                rvSearchNews.setPadding(0, 0, 0, 0)
            }
        }
    }

    private fun handleError(response: Resource<NewsResponse>) {
        hideProgressBar()
        response.message?.let { message ->
            Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingPageAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingPageAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getSearchNewsCall(
                    etSearch.text.toString(),
                    requireContext().hasInternetConnection()
                )
                isScrolling = false
            }
        }
    }


    private fun setupRecyclerView() {
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }

    private fun navigate(article: ArticleRemote) {
        findNavController().navigate(
            SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(
                article,
                null
            )
        )
    }

    private companion object {
        const val SEARCH_NEWS_TIME_DELAY = 500L
    }
}