package com.example.myapplication.data

import com.example.data.model.Article
import com.example.data.model.News
import com.example.data.model.Source
import com.example.data.util.network.NetworkResponse
import com.example.myapplication.MainCoroutineRule
import com.example.myapplication.data.local.NewsDao
import com.example.myapplication.data.model.LastEnteredTime
import com.example.myapplication.data.model.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.util.*

class NewsRepositoryImplTest {
    lateinit var repository: NewsRepositoryImpl
    lateinit var api: FactServiceApi
    lateinit var dao: NewsDao

    val newsList = listOf(
        Article(
            source = Source("", ""),
            author = "adf",
            title = "nurs",
            description = "nurs",
            url = "nurs",
            urlToImage = "nurs",
            publishedAt = "nurs",
            content = "dfa",
            articleId = 1
        )
    )
    val news = News(status = "200", totalResults = 1, articles = newsList, id = 1)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        api = mockk()
        dao = mockk()
        repository = NewsRepositoryImpl(api, dao)
        setup()
    }

    @Test
    fun `if shouldUpdate false and db has data, data must come from db`() = runBlockingTest {
        val h = repository.getNews(false)
        io.mockk.coVerifySequence {
            dao.getArticles(0)
        }
        coVerify(exactly = 0){
            api.getNews(any(),any(),any(),any())
        }
        assertEquals(Result.Success(newsList), h)
    }

    @Test
    fun testGetNews() {
    }

    @Test
    fun getLastEnteredTime() {
    }

    @Test
    fun saveLastEnteredTime() {
    }

    fun setup() {
        coEvery {
            api.getNews(any(), any(), any(), any())
        } returns NetworkResponse.Success(news)


        coEvery {
            dao.getArticles(0)
        } returns newsList


        coEvery {
            dao.saveArticles(*newsList.toTypedArray())
        } returns Unit

        coEvery {
            dao.deleteAll()
        } returns Unit

        coEvery {
            dao.getLastEnteredTime()
        } returns LastEnteredTime(Date().time)

        coEvery {
            dao.saveLastEnteredTime(LastEnteredTime(Date().time))
        } returns Unit

    }
}