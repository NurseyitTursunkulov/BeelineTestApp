package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.GetNewsUseCase
import com.example.myapplication.presenter.NewsPresenterState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val presenterState: NewsPresenterState
) : ViewModel(), NewsPresenterState by presenterState {
    
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val shouldUpdate = getNewsUseCase.shouldUpdate()
                getNews(shouldUpdate)
            }
        }

    }

    fun getNews(shouldUpdate:Boolean = false) {
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