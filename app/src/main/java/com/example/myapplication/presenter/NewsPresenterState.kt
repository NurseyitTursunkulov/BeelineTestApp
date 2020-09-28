package com.example.myapplication.presenter

import androidx.lifecycle.LiveData
import com.example.data.model.Article
import io.aikosoft.alaket.util.Event
import java.lang.Exception

interface NewsPresenterState {

    val showLoadingEvent: LiveData<Event<Boolean>>
    val displayNewsEvent: LiveData<Event<MutableList<Article>>>
    val showErrorEvent: LiveData<Event<Exception>>
}