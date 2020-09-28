package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Article
import com.example.data.util.network.NetworkResponse
import com.example.myapplication.data.FactServiceApi
import com.example.myapplication.domain.GetNewsUseCase
import com.example.myapplication.presenter.NewsPresenter
import com.example.myapplication.presenter.NewsPresenterState
import io.aikosoft.alaket.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

class MainViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val presenterState: NewsPresenterState
) : ViewModel(), NewsPresenterState by presenterState {
    
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val g = getNewsUseCase.shouldUpdate()
                get(g)
            }
        }

    }

    fun get(shouldUpdate:Boolean = false) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getNewsUseCase.getNews(shouldUpdate)
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

    override fun onCleared() {
        super.onCleared()
        presenterState.displayNewsEvent.value?.peekContent()?.clear()
    }

}