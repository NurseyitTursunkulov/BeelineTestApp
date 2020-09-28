package com.example.myapplication.presenter

import androidx.lifecycle.LiveData
import com.example.data.model.Article
import io.aikosoft.alaket.util.Event
import java.lang.Exception

interface NewsPresenterState {

    //    todo instead of liveData here supposed to be Kotlin Flow, but there were some bug
    //     with it, so we postponed it until improvement
    val showLoadingEvent: LiveData<Event<Boolean>>
    val displayNewsEvent: LiveData<Event<MutableList<Article>>>
    val showErrorEvent: LiveData<Event<Exception>>
}