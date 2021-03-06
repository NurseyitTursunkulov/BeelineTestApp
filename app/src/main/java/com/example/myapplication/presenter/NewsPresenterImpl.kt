package com.example.myapplication.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.model.Article
import io.aikosoft.alaket.util.Event
import java.lang.Exception

class NewsPresenterImpl :NewsPresenter ,NewsPresenterState{

    private val _showLoadingEvent = MutableLiveData<Event<Boolean>>()
    override val showLoadingEvent: LiveData<Event<Boolean>> = _showLoadingEvent
    override suspend fun showLoading(state: Boolean) {
        _showLoadingEvent.postValue(Event(state))
    }

    private val _displayNewsEvent = MutableLiveData<Event<MutableList<Article>>>()
    override val displayNewsEvent: LiveData<Event<MutableList<Article>>> = _displayNewsEvent
    override suspend fun displayNews(list: List<Article>) {
        val listOfArticles = mutableListOf<Article>()
        displayNewsEvent.value?.peekContent()?.let {
            listOfArticles.addAll(it)
        }
        listOfArticles.addAll(list)
        _displayNewsEvent.postValue(Event(listOfArticles))
    }

    private val _showErrorEvent = MutableLiveData<Event<Exception>>()
    override val showErrorEvent: LiveData<Event<Exception>> = _showErrorEvent
    override suspend fun showError(list: Exception) {
        _showErrorEvent.postValue(Event(list))
    }

    override suspend fun clearOldNews() {
        _displayNewsEvent.value?.peekContent()?.clear()
    }
}