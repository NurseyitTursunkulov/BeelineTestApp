package com.example.myapplication.domain

import com.example.myapplication.data.NewsRepository
import com.example.myapplication.data.model.Result
import com.example.myapplication.presenter.NewsPresenter

class GetNewsUseCase(
    private val repository: NewsRepository,
    private val presenter: NewsPresenter
) {
    suspend fun getNews() {
        presenter.showLoading(true)
        val result = repository.getNews()
        when (result) {
            is Result.Success -> {
                presenter.displayNews(result.data.articles)
            }
            is Result.Error -> {
                presenter.showError(result.exception)
            }
        }
        presenter.showLoading(false)
    }

    suspend fun getNextPage(page:Int){
        presenter.showLoading(true)
        val result = repository.getNews(page)
        when (result) {
            is Result.Success -> {
                presenter.displayNews(result.data.articles)
            }
            is Result.Error -> {
                presenter.showError(result.exception)
            }
        }
        presenter.showLoading(false)
    }
}