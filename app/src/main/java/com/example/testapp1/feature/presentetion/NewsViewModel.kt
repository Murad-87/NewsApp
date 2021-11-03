package com.example.testapp1.feature.presentetion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.business.BreakingNewsInteractor
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.business.SearchedNewsInteractor
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    private val savedNewsInteractor: SavedNewsInteractor,
    private val breakingNewsInteractor: BreakingNewsInteractor,
    private val searchedNewsInteractor: SearchedNewsInteractor
) : ViewModel() {

    private val breakingNewsMutable: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNews: LiveData<Resource<NewsResponse>>
        get() = breakingNewsMutable

    var breakingNewsPage = 1
    private var breakingNewsResponse: NewsResponse? = null

    private val searchNewsMutable: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: LiveData<Resource<NewsResponse>>
        get() = searchNewsMutable

    var searchNewsPage = 1
    private var searchNewsResponse: NewsResponse? = null

    //TODO: make call in viewCreate() to getBreakingNews()

    private fun getBreakingNewsCall(countryCode: String, hasInternetConnection: Boolean) {
        breakingNewsMutable.postValue(Resource.Loading())
        try {
            if (hasInternetConnection) {
                viewModelScope.launch(Dispatchers.IO) {
                    breakingNewsMutable.postValue(
                        handleBreakingNewsResponse(
                            breakingNewsInteractor.get(countryCode, breakingNewsPage)
                        )
                    )
                }
            } else {
                breakingNewsMutable.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNewsMutable.postValue(Resource.Error("Network Failure"))
                else -> breakingNewsMutable.postValue(Resource.Error("Conversion Error"))
            }
        }
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

    private fun getSearchNewsCall(searchQuery: String, hasInternetConnection: Boolean) {
        searchNewsMutable.postValue(Resource.Loading())
        try {
            if (hasInternetConnection) {
                viewModelScope.launch(Dispatchers.IO) {
                    searchNewsMutable.postValue(handleSearchNewsResponse(
                        searchedNewsInteractor.get(searchQuery, searchNewsPage)
                        )
                    )
                }
            } else {
                searchNewsMutable.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNewsMutable.postValue(Resource.Error("Network Failure"))
                else -> searchNewsMutable.postValue(Resource.Error("Conversion Error"))
            }
        }
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