package com.example.myapplication.domain

import com.example.myapplication.data.NewsRepository
import com.example.myapplication.data.model.Result
import com.example.myapplication.presenter.NewsPresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
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

    suspend fun getNextPage(page: Int) {
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
    private val MILLISECOND = 1000

    suspend fun shouldUpdate(): Boolean {
        repository.getLastEnteredTime()?.let {time->
            Timber.d("${((Date().time - time)/MILLISECOND)}")
            return (Date().time - time)/MILLISECOND > UPDATE_INTERVAL
        }?:
        return false
    }
}