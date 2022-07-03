package com.example.testapp1.presentation.breakingNewsFragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.R
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.databinding.FragmentBreakingNewsBinding
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
import com.example.testapp1.presentation.breakingNewsFragment.presentation.BreakingNewsViewModel
import com.example.testapp1.presentation.searchNewsFragment.ui.SearchNewsFragment
import com.example.testapp1.presentation.ui.NewsAdapter
import com.example.testapp1.utils.BaseClasses.BaseFragment
import com.example.testapp1.utils.Resource
import com.example.testapp1.utils.hasInternetConnection
import com.example.testapp1.utils.visibilityIf
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

        viewModel.getBreakingNews(
            getString(R.string.country_code),
            requireContext().hasInternetConnection()
        )

        viewModel.breakingNews.observe(viewLifecycleOwner, { response ->
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
            rvBreakingNews.visibilityIf(hasConnection)
            noConnectionImageviewBreaking.visibilityIf(!hasConnection)
            noConnectionTitleTextViewBreaking.visibilityIf(!hasConnection)
            noConnectionMessageTextViewBreaking.visibilityIf(!hasConnection)
        }
    }

    private fun handleSuccess(response: Resource<NewsResponse>) {
        progressBarVisibility(false)
        response.data?.let { newsResponse ->
            newsAdapter.submitList(newsResponse.articles.toList())
            val totalPages = newsResponse.totalResults / SearchNewsFragment.QUERY_PAGE_SIZE + 2
            isLastPage = viewModel.breakingNewsPage == totalPages
            if (isLastPage) {
                rvBreakingNews.setPadding(0, 0, 0, 0)
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
            ).show()
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

    private fun navigate(articleRemote: ArticleRemote) {
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
        }
    }
}