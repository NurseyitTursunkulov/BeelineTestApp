package com.example.myapplication

import com.example.data.model.Article
import com.example.data.model.Source
import com.example.myapplication.data.NewsRepository
import com.example.myapplication.data.model.Result
import com.example.myapplication.domain.GetNewsUseCase
import com.example.myapplication.presenter.NewsPresenter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.lang.Exception
import java.util.*

class GetNewsUseCaseUnitTest {
    lateinit var getNewsUseCase: GetNewsUseCase
    lateinit var repository: NewsRepository
    lateinit var presenter: NewsPresenter
    val newsList = listOf(Article(
        source = Source("",""),
                author = "adf",
                title = "nurs",
                description = "nurs",
                url = "nurs",
                urlToImage = "nurs",
                publishedAt = "nurs",
                content = "dfa",
                articleId = 1
    ))
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp(){
        repository = mockk()
        presenter = mockk()
        getNewsUseCase = GetNewsUseCase(repository,presenter)
        stubLoginPresenter()
    }

    @Test
    fun `repository returns success`() = runBlockingTest {
        getNewsUseCase.getNews()
        io.mockk.coVerifySequence {
            presenter.showLoading(true)
            repository.getNews(false)
            presenter.displayNews(newsList)
            presenter.showLoading(false)
        }
    }

    @Test
    fun `repository returns failure`() = runBlockingTest {
        coEvery {
            repository.getNews(false)
        } returns Result.Error(Exception())
        getNewsUseCase.getNews()
        io.mockk.coVerifySequence {
            presenter.showLoading(true)
            repository.getNews(false)
            presenter.showError(any())
            presenter.showLoading(false)
        }
    }

    @Test
    fun `if shouldUpdate presenter clearOldNews called`() = runBlockingTest {
        getNewsUseCase.getNews(true)
        io.mockk.coVerifySequence {
            presenter.showLoading(true)
            repository.getNews(true)
            presenter.clearOldNews()
            presenter.displayNews(newsList)
            presenter.showLoading(false)
        }
    }


    @Test
    fun `getNextPage success call right presenter methods`()= runBlockingTest {
        getNewsUseCase.getNextPage(1)
        io.mockk.coVerifySequence {
            presenter.showLoading(true)
            repository.getNews(1)
            presenter.displayNews(newsList)
            presenter.showLoading(false)
        }
    }

    @Test
    fun `getNextPage failure call right presenter methods`()= runBlockingTest {
        val e = Exception()
        coEvery {
            repository.getNews(1)
        } returns Result.Error(e)
        getNewsUseCase.getNextPage(1)
        io.mockk.coVerifySequence {
            presenter.showLoading(true)
            repository.getNews(1)
            presenter.showError(e)
            presenter.showLoading(false)
        }
    }



    private fun stubLoginPresenter() {
        coEvery {
            presenter.showLoading(true)
        } returns Unit
        coEvery {
            presenter.showLoading(false)
        } returns Unit
        coEvery {
            repository.getNews(false)
        } returns Result.Success(newsList)
        coEvery {
            repository.getNews(true)
        } returns Result.Success(newsList)
        coEvery {
            presenter.displayNews(any())
        } returns Unit
        coEvery {
            presenter.showError(any())
        } returns Unit
        coEvery {
            presenter.clearOldNews()
        } returns Unit
        coEvery {
            repository.getNews(1)
        } returns Result.Success(newsList)
        coEvery {
            repository.getLastEnteredTime()
        } returns Date().time
        coEvery {
            repository.saveLastEnteredTime(any())
        } returns Unit
    }
}