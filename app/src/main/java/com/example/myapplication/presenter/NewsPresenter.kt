package com.example.myapplication.presenter

import com.example.data.model.Article
import java.lang.Exception

interface NewsPresenter {
    suspend fun showLoading(state: Boolean)

    suspend fun displayNews(list: List<Article>)

    suspend fun showError(list: Exception)
}