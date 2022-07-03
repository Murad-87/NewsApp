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
import com.example.testapp1.R
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.databinding.FragmentSearchNewsBinding
import com.example.testapp1.di.app.ApplicationContextModule
import com.example.testapp1.di.app.DaggerApplicationComponent
import com.example.testapp1.di.data.component.DaggerDataComponent
import com.example.testapp1.di.data.module.LocaleModule
import com.example.testapp1.di.data.module.RemoteModule
import com.example.testapp1.di.data.module.RepositoryModule
import com.example.testapp1.di.domain.component.DaggerDomainComponent
import com.example.testapp1.di.domain.module.InteractorModule
import com.example.testapp1.di.feature.component.DaggerFeatureComponent
import com.example.testapp1.di.feature.module.ViewModelFactory
import com.example.testapp1.presentation.searchNewsFragment.presentation.SearchNewsViewModel
import com.example.testapp1.presentation.ui.NewsAdapter
import com.example.testapp1.utils.BaseClasses.BaseFragment
import com.example.testapp1.utils.Resource
import com.example.testapp1.utils.hasInternetConnection
import com.example.testapp1.utils.visibilityIf
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
        DaggerFeatureComponent
            .builder()
            .domainComponent(
                DaggerDomainComponent.builder()
                    .interactorModule(InteractorModule())
                    .dataComponent(
                        DaggerDataComponent.builder()
                            .localeModule(LocaleModule())
                            .remoteModule(RemoteModule())
                            .repositoryModule(RepositoryModule())
                            .applicationComponent(
                                DaggerApplicationComponent.builder()
                                    .applicationContextModule(
                                        ApplicationContextModule(
                                            requireActivity().application
                                        )
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

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
                is Resource.LocalError -> {
                    handleLocalError(response)
                }
                is Resource.Loading -> {
                    progressBarVisibility(true)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        changeVisibilityIfHasConnection(requireContext().hasInternetConnection())
    }

    private fun changeVisibilityIfHasConnection(hasConnection: Boolean) {
        with(binding) {
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
                rvSearchNews.setPadding(0, 0, 0, 0)
            }
        }
    }

    private fun handleError(response: Resource<NewsResponse>) {
        progressBarVisibility(false)
        response.message?.let { message ->
            Toast.makeText(
                requireContext(),
                String.format(getString(R.string.error_message, message)),
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    private fun handleLocalError(response: Resource<NewsResponse>) {
        progressBarVisibility(false)
        response.localMessage?.let { message ->
            Toast.makeText(
                requireContext(),
                String.format(getString(R.string.error_message), getText(message)),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun progressBarVisibility(isVisible: Boolean) {
        binding.paginationProgressBar.visibilityIf(isVisible)
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
        binding.rvSearchNews.apply {
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
    }
}