package com.example.testapp1.presentation.searchNewsFragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.NewsApplication
import com.example.testapp1.R
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.databinding.FragmentSearchNewsBinding
import com.example.testapp1.di.ViewModelFactory
import com.example.testapp1.presentation.searchNewsFragment.presentation.SearchNewsViewModel
import com.example.testapp1.presentation.breakingNewsFragment.ui.recyclerView.NewsAdapter
import com.example.testapp1.utils.*
import com.example.testapp1.utils.BaseClasses.BaseFragment
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchNewsFragment :
    BaseFragment<FragmentSearchNewsBinding>(FragmentSearchNewsBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchNewsViewModel by viewModels {
        viewModelFactory
    }
    private val newsAdapter by lazy { NewsAdapter() }

    private var isLoading = false
    private var isLastPage = false

    override fun onAttach(context: Context) {
        val component = (requireActivity().application as NewsApplication).component
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        newsAdapter.setOnItemClickListener {
            navigate(it)
        }

        delayedNewsSearch()

        viewModel.searchNewsMutable.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    handleSuccess(response)
                }
                is Resource.Error -> {
                    handleError(response)
                }
                is Resource.LocalError -> {
                    handleLocalError(response)
                }
                is Resource.Loading -> {
                    progressBarVisibility(true)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        changeVisibilityIfHasConnection(requireContext().hasInternetConnection())
    }

    private fun changeVisibilityIfHasConnection(hasConnection: Boolean) {
        with(viewBinding) {
            rvSearchNews.visibilityIf(hasConnection)
            noConnectionImageviewSearch.visibilityIf(!hasConnection)
            noConnectionTitleTextViewSearch.visibilityIf(!hasConnection)
            noConnectionMessageTextViewSearch.visibilityIf(!hasConnection)
        }
    }

    private fun delayedNewsSearch() {
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchQuery = editable.toString()
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
        progressBarVisibility(false)
        response.data?.let { newsResponse ->
            newsAdapter.submitList(newsResponse.articles.toList())
            val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
            isLastPage = viewModel.searchNewsPage == totalPages
            if (isLastPage) {
                rvSearchNews.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            }
        }
    }

    private fun handleError(response: Resource<NewsResponse>) {
        progressBarVisibility(false)
        response.message?.let { message ->
            showToastLL(String.format(getString(R.string.error_message, message)))
        }
    }

    private fun handleLocalError(response: Resource<NewsResponse>) {
        progressBarVisibility(false)
        response.localMessage?.let { message ->
            showToastLS(String.format(getString(R.string.error_message), getText(message)))
        }
    }

    private fun progressBarVisibility(isVisible: Boolean) {
        viewBinding.paginationProgressBar.visibilityIf(isVisible)
        isLoading = isVisible
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        private var isScrolling = false

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
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingPageAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getSearchNewsCall(
                    etSearch.text.toString(),
                    requireContext().hasInternetConnection(),
                    true
                )
                isScrolling = false
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }

    private fun navigate(article: ArticleRemote) {
        findNavController().navigate(
            SearchNewsFragmentDirections
                .actionSearchNewsFragmentToArticleFragment(
                    article,
                    null
                )
        )
    }

    companion object {
        private const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20
        const val LEFT_PADDING = 0
        const val RIGHT_PADDING = 0
        const val TOP_PADDING  = 0
        const val BOTTOM_PADDING = 0
    }
}