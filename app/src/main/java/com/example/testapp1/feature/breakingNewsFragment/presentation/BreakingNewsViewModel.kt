package com.example.testapp1.feature.breakingNewsFragment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.R
import com.example.testapp1.business.BreakingNewsUseCase
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class BreakingNewsViewModel @Inject constructor(
    private val breakingNewsUseCase: BreakingNewsUseCase
) : ViewModel() {

    private val breakingNewsMutable: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNews: LiveData<Resource<NewsResponse>>
        get() = breakingNewsMutable

    var breakingNewsPage = 1
    private var breakingNewsResponse: NewsResponse? = null

    fun getBreakingNews(countryCode: String, hasInternetConnection: Boolean) {
        breakingNewsMutable.postValue(Resource.Loading())
        try {
            if (hasInternetConnection) {
                viewModelScope.launch(Dispatchers.IO) {
                    breakingNewsMutable.postValue(
                        handleBreakingNewsResponse(
                            breakingNewsUseCase.get(countryCode, breakingNewsPage)
                        )
                    )
                }
            } else {
                breakingNewsMutable.postValue(Resource.LocalError(R.string.error_no_internet_connection))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNewsMutable.postValue(Resource.LocalError(R.string.error_network_failure))
                else -> breakingNewsMutable.postValue(Resource.LocalError(R.string.conversion_error))
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
}