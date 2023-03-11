package com.example.testapp1.presentation.breakingNewsFragment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.R
import com.example.testapp1.data.remote.model.NewsDataResponse
import com.example.testapp1.domain.BreakingNewsUseCase
import com.example.testapp1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class BreakingNewsViewModel @Inject constructor(
    private val breakingNewsUseCase: BreakingNewsUseCase
) : ViewModel() {

    private val _breakingNewsMutable: MutableLiveData<Resource<NewsDataResponse>> =
        MutableLiveData()
    val breakingNewsMutable: LiveData<Resource<NewsDataResponse>> = _breakingNewsMutable

    var breakingNewsPage = 1
    private var breakingNewsResponse: NewsDataResponse? = null

    fun getBreakingNews(countryCode: String, hasInternetConnection: Boolean) {
        _breakingNewsMutable.postValue(Resource.Loading())
        try {
            if (hasInternetConnection) {
                viewModelScope.launch(Dispatchers.IO) {
                    _breakingNewsMutable.postValue(
                        handleBreakingNewsResponse(
                            breakingNewsUseCase.get(countryCode)
                        )
                    )
                }
            } else {
                _breakingNewsMutable.postValue(Resource.LocalError(R.string.error_no_internet_connection))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _breakingNewsMutable.postValue(Resource.LocalError(R.string.error_network_failure))
                else -> _breakingNewsMutable.postValue(Resource.LocalError(R.string.conversion_error))
            }
        }
    }

    private fun handleBreakingNewsResponse(response: NewsDataResponse): Resource<NewsDataResponse> {
        if (response.status.isNotEmpty()) {
            response.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.results
                    val newArticles = resultResponse.results
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error("")
    }
}