package com.example.testapp1.feature.searchNewsFragment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.business.SearchedNewsInteractor
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class SearchNewsViewModel(private val searchedNewsInteractor: SearchedNewsInteractor) : ViewModel() {

    private val searchNewsMutable: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: LiveData<Resource<NewsResponse>>
        get() = searchNewsMutable

    var searchNewsPage = 1
    private var searchNewsResponse: NewsResponse? = null

    fun getSearchNewsCall(searchQuery: String, hasInternetConnection: Boolean) {
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
}