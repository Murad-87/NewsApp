package com.example.testapp1.presentation.searchNewsFragment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.R
import com.example.testapp1.domain.SearchedNewsUseCase
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class SearchNewsViewModel @Inject constructor(
    private val searchedNewsUseCase: SearchedNewsUseCase
) : ViewModel() {
    //TODO: handle second search for the same query after coming back

    private val _searchNewsMutable: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsMutable: LiveData<Resource<NewsResponse>> = _searchNewsMutable

    var searchNewsPage = 1
    private var searchNewsResponse: NewsResponse? = null

    var searchQuery: String? = null

    fun getSearchNewsCall(
        searchQuery: String,
        hasInternetConnection: Boolean,
        shouldPaginate: Boolean = false
    ) {
        _searchNewsMutable.postValue(Resource.Loading())
        try {
            if (hasInternetConnection) {
                viewModelScope.launch(Dispatchers.IO) {
                    _searchNewsMutable.postValue(
                        handleSearchNewsResponse(
                            searchedNewsUseCase.get(searchQuery, searchNewsPage),
                            shouldPaginate
                        )
                    )
                }
            } else {
                _searchNewsMutable.postValue(Resource.LocalError(R.string.error_no_internet_connection))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _searchNewsMutable.postValue(Resource.LocalError(R.string.error_network_failure))
                else -> _searchNewsMutable.postValue(Resource.LocalError(R.string.conversion_error))
            }
        }
    }

    private fun handleSearchNewsResponse(
        response: Response<NewsResponse>,
        shouldPaginate: Boolean
    ): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    if (!oldArticles.isNullOrEmpty() && !shouldPaginate) oldArticles.clear()
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}