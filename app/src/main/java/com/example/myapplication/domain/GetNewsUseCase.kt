package com.example.myapplication.domain

import com.example.myapplication.data.NewsRepository
import com.example.myapplication.data.model.Result
import com.example.myapplication.presenter.NewsPresenter
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import timber.log.Timber.d
import java.util.*

class GetNewsUseCase(
    private val repository: NewsRepository,
    private val presenter: NewsPresenter
) {

    suspend fun getNews(shouldUpdate: Boolean = false) {
        presenter.showLoading(true)
        val result = repository.getNews(shouldUpdate)
        if (shouldUpdate){
            presenter.clearOldNews()
        }
        when (result) {
            is Result.Success -> {
                presenter.displayNews(result.data)
            }
            is Result.Error -> {
                presenter.showError(result.exception)
            }
        }
        presenter.showLoading(false)
    }

    suspend fun getNextPage(page: Int, shouldUpdate: Boolean = false) {
        presenter.showLoading(true)
        val result = repository.getNews(page)
        when (result) {
            is Result.Success -> {
                presenter.displayNews(result.data)
            }
            is Result.Error -> {
                presenter.showError(result.exception)
            }
        }
        presenter.showLoading(false)
    }

    private val UPDATE_INTERVAL = 70

    suspend fun shouldUpdate(): Boolean {
        repository.getLastEnteredTime()?.let {
            Timber.d("${((Date().time - it)/1000)}")
            return (Date().time - it)/1000 > UPDATE_INTERVAL
        }?:
        return false
    }
}