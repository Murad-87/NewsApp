package com.example.testapp1.feature.presentetion

import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    private val savedNewsInteractor: SavedNewsInteractor
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    //TODO: make call in viewCreate() to getBreakingNews()

    fun getBreakingNews(countryCode: String, hasInternetConnection: Boolean) = viewModelScope.launch {
        safeBreakingNewsCall(countryCode, hasInternetConnection)
    }

    fun searchNews(searchQuery: String, hasInternetConnection: Boolean) = viewModelScope.launch {
        safeSearchNewsCall(searchQuery, hasInternetConnection)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun safeSearchNewsCall(searchQuery: String, hasInternetConnection: Boolean) {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun safeBreakingNewsCall(countryCode: String, hasInternetConnection: Boolean) {
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    fun saveArticle(article: ArticleRemote) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsInteractor.save(article)
        }
    }

    fun getSavedNews() = savedNewsInteractor.flow()

    fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsInteractor.delete(article)
        }
    }
}