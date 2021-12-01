package com.example.testapp1.feature.savedNewsFragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.R
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.databinding.FragmentSavedNewsBinding
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
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModel
import com.example.testapp1.utils.BaseClasses.BaseFragment
import com.example.testapp1.utils.visibilityIf
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*
import javax.inject.Inject


class SavedNewsFragment :
    BaseFragment<FragmentSavedNewsBinding>(FragmentSavedNewsBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SavedNewsViewModel by viewModels {
        viewModelFactory
    }
    private val newsAdapter by lazy { SavedNewsAdapter() }

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
        initTouchListener()

        newsAdapter.setOnItemClickListener {
            navigate(it)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            if (it.isNotEmpty()) changeVisibilityIfNoArticles(true)
            if (it.isEmpty()) changeVisibilityIfNoArticles(false)
        }
    }

    private fun initTouchListener() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(
                    requireView(),
                    getString(R.string.successfully_deleted_article),
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        setAction(getString(R.string.undo)) {
                            viewModel.reloadArticle(article)
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
    }

    private fun navigate(articleEntity: ArticleEntity) {
        findNavController().navigate(
            SavedNewsFragmentDirections
                .actionSavedNewsFragmentToArticleFragment(
                    null,
                    articleEntity
                )
        )
    }

    private fun initRecyclerView() {
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun changeVisibilityIfNoArticles(hasArticles: Boolean) {
        with(binding) {
            rvSavedNews.visibilityIf(hasArticles)
            noSavedArticlesImageView.visibilityIf(!hasArticles)
            noSavedArticlesTitleTextView.visibilityIf(!hasArticles)
            noSavedArticlesDescriptionTextView.visibilityIf(!hasArticles)
        }
    }
}