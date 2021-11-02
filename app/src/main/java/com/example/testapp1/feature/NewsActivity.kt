package com.example.testapp1.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testapp1.R
import com.example.testapp1.databinding.ActivityNewsBinding

import com.example.testapp1.feature.presentetion.NewsViewModel

import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityNewsBinding
    lateinit var navController: NavController
    lateinit var viewModel: NewsViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val newsRepository = NewsRepository(ArticleDatabase(this))
//        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
            navController = navHostFragment.findNavController()

        bottomNavigationView.setupWithNavController(navController)

       // binding.bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }
}