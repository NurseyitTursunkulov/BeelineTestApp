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
            }
        }
    }

    fun getNextPage(page:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getNewsUseCase.getNextPage(page)
            }
        }
    }

}