package com.example.testapp1.presentation.breakingNewsFragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.NewsApplication
import com.example.testapp1.R
import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.data.remote.model.NewsDataResponse
import com.example.testapp1.databinding.FragmentBreakingNewsBinding
import com.example.testapp1.di.ViewModelFactory
import com.example.testapp1.presentation.breakingNewsFragment.presentation.BreakingNewsViewModel
import com.example.testapp1.presentation.breakingNewsFragment.ui.recyclerView.NewsAdapter
import com.example.testapp1.presentation.searchNewsFragment.ui.SearchNewsFragment
import com.example.testapp1.utils.*
import com.example.testapp1.utils.baseClasses.BaseFragment
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import javax.inject.Inject

class BreakingNewsFragment :
    BaseFragment<FragmentBreakingNewsBinding>(FragmentBreakingNewsBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: BreakingNewsViewModel by viewModels {
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

        viewModel.getBreakingNews(
            getString(R.string.country_code),
            requireContext().hasInternetConnection()
        )

        viewModel.breakingNewsMutable.observe(viewLifecycleOwner) { response ->
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
            rvBreakingNews.visibilityIf(hasConnection)
            noConnectionImageviewBreaking.visibilityIf(!hasConnection)
            noConnectionTitleTextViewBreaking.visibilityIf(!hasConnection)
            noConnectionMessageTextViewBreaking.visibilityIf(!hasConnection)
        }
    }

    private fun handleSuccess(response: Resource<NewsDataResponse>) {
        progressBarVisibility(false)
        response.data?.let { newsResponse ->
            newsAdapter.submitList(newsResponse.results.toList())
            val totalPages = newsResponse.totalResults / SearchNewsFragment.QUERY_PAGE_SIZE + 2
            isLastPage = viewModel.breakingNewsPage == totalPages
            if (isLastPage) {
                rvBreakingNews.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            }
        }
    }

    private fun handleError(response: Resource<NewsDataResponse>) {
        progressBarVisibility(false)
        response.message?.let { message ->
            showToastLL(String.format(getString(R.string.error_message, message)))
        }
    }

    private fun handleLocalError(response: Resource<NewsDataResponse>) {
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
            val isTotalMoreThanVisible = totalItemCount >= SearchNewsFragment.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingPageAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews(
                    getString(R.string.country_code),
                    requireContext().hasInternetConnection()
                )
                isScrolling = false
            }
        }
    }

    private fun navigate(articleRemote: NewArticleRemote) {
        findNavController().navigate(
            BreakingNewsFragmentDirections
                .actionBreakingNewsFragmentToArticleFragment(
                    articleRemote,
                    null
                )
        )
    }

    private fun initRecyclerView() {
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    companion object {
        const val LEFT_PADDING = 0
        const val RIGHT_PADDING = 0
        const val TOP_PADDING = 0
        const val BOTTOM_PADDING = 0
    }
}