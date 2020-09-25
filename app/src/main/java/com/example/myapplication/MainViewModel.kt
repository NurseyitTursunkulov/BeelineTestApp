package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.util.network.NetworkResponse
import com.example.myapplication.data.FactServiceApi
import com.example.myapplication.domain.GetNewsUseCase
import com.example.myapplication.presenter.NewsPresenterState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val presenterState: NewsPresenterState
) : ViewModel(), NewsPresenterState by presenterState {

    fun get() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getNewsUseCase.getNews()
//                Timber.d("News  =  ${factServiceApi.getNews("us","ffb0a70e60274c8d955b64776e69e100")}")
//                val res = factServiceApi.getNews( "us","ffb0a70e60274c8d955b64776e69e100")
//                 when (res) {
//                    is NetworkResponse.Success -> {
//                        Log.d("Nurs","News  = $res")
//                    }
//                    is NetworkResponse.ApiError -> {
//                        Timber.d("News  = ${res.body.message.toString()}")
//                    }
//                    is NetworkResponse.NetworkError -> {
//                        Timber.d("News  = NetworkError $res")
//                    }
//                    is NetworkResponse.UnknownError -> {
//                        Timber.d("News  = UnknownError $res")
//                    }
//                }
            }
        }
    }

}