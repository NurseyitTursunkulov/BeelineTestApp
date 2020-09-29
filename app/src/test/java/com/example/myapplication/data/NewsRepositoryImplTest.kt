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
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.ArgumentMatchers.any
import java.io.IOException
import java.util.*

class NewsRepositoryImplTest {
    lateinit var repository: NewsRepositoryImpl
    lateinit var api: FactServiceApi
    lateinit var dao: NewsDao

    private val newsListFromDB = listOf(
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

    private val newsListFromApi = listOf(
        Article(
            source = Source("adf", ""),
            author = "api",
            title = "api",
            description = "api",
            url = "api",
            urlToImage = "api",
            publishedAt = "api",
            content = "api",
            articleId = 1
        )
    )
    val news = News(status = "200", totalResults = 1, articles = newsListFromApi, id = 1)

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
        val data = repository.getNews(false)
        io.mockk.coVerifySequence {
            dao.getArticles(0)
        }
        coVerify(exactly = 0){
            api.getNews(any(),any(),any(),any())
        }
        assertEquals(Result.Success(newsListFromDB), data)
    }
    @Test
    fun `if shouldUpdate false and db has no data, data must come from api`() = runBlockingTest {
        coEvery {
            dao.getArticles(0)
        } returns emptyList()
        val data = repository.getNews(false)
        io.mockk.coVerifySequence {
            dao.getArticles(0)
            api.getNews(any(),any(),any(),any())
            dao.saveArticles(*newsListFromDB.toTypedArray())
            dao.saveLastEnteredTime(any())
        }
        coVerify(exactly = 0){
            dao.deleteAll()
        }
        assertEquals(Result.Success(newsListFromDB), data)
    }

    @Test
    fun `if shouldUpdate data must come from api`()= runBlockingTest{
        val data = repository.getNews(true)
        io.mockk.coVerifySequence {
            dao.deleteAll()
            api.getNews(any(),any(),any(),any())
            dao.saveArticles(*newsListFromDB.toTypedArray())
            dao.saveLastEnteredTime(any())
        }
        coVerify(exactly = 0){
            dao.getArticles(0)
        }
        assertEquals(Result.Success(newsListFromDB), data)
    }

    @Test
    fun `testGetNews db has data`() = runBlockingTest{
        val data = repository.getNews(1)
        io.mockk.coVerifySequence {
            dao.getArticles(1)
        }
        coVerify(exactly = 0){
            dao.deleteAll()
            api.getNews(any(),any(),any(),any())
            dao.saveArticles(*newsListFromDB.toTypedArray())
            dao.saveLastEnteredTime(any())
        }
        assertEquals(Result.Success(newsListFromDB), data)
    }

    @Test
    fun `testGetNews db has no data`() = runBlockingTest{
        coEvery {
            dao.getArticles(any())
        } returns emptyList()

        val data = repository.getNews(1)
        io.mockk.coVerifySequence {
            dao.getArticles(1)
            api.getNews(any(),any(),any(),any())
            dao.saveArticles(*newsListFromApi.toTypedArray())
            dao.saveLastEnteredTime(any())
        }
        assertEquals(Result.Success(newsListFromApi), data)
    }

    @Test
    fun `if GetNewsApi fails it must return Result Error`() = runBlockingTest{
        coEvery {
            dao.getArticles(any())
        } returns emptyList()
        coEvery {
            api.getNews(any(),any(),any(),any())
        } returns NetworkResponse.NetworkError(IOException())

        val data = repository.getNews(1)
        Assert.assertTrue(data is Result.Error)
    }

    fun setup() {
        coEvery {
            api.getNews(any(), any(), any(), any())
        } returns NetworkResponse.Success(news)


        coEvery {
            dao.getArticles(any())
        } returns newsListFromDB


        coEvery {
            dao.saveArticles(any())
        } returns Unit

        coEvery {
            dao.deleteAll()
        } returns Unit

        coEvery {
            dao.getLastEnteredTime()
        } returns LastEnteredTime(Date().time)

        coEvery {
            dao.saveLastEnteredTime(any())
        } returns Unit

    }
}