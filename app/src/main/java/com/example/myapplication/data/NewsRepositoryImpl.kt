package com.example.myapplication.data

import android.util.Log
import com.example.data.model.Article
import com.example.data.model.News
import com.example.data.util.network.NetworkResponse
import com.example.myapplication.data.local.NewsDao
import com.example.myapplication.data.model.LastEnteredTime
import com.example.myapplication.data.model.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.util.*

class NewsRepositoryImpl(
    private val api: FactServiceApi,
    private val dao: NewsDao
) : NewsRepository {

    override suspend fun getNews(shouldUpdate:Boolean): Result<List<Article>> {
        if (shouldUpdate){
            dao.deleteAll()
        }
        else {
            dao.getArticles(0)?.let {
                Timber.d("from dao ${it.size}")
                if (it.isNotEmpty()) {
                    return Result.Success(it)
                }
            }
        }
        val res = api.getNews(q = "Россия", key = "ffb0a70e60274c8d955b64776e69e100",pageSize = 10,page = 1)
        return when (res) {
            is NetworkResponse.Success -> {
                res.body.articles.mapIndexed { index, article ->
                    article.articleId = index +1
                }
                Timber.d("from net ${res.body.articles.size}")
                dao.saveArticles(*res.body.articles.toTypedArray())
                saveLastEnteredTime(Date().time)
                Result.Success(res.body.articles)
            }
            is NetworkResponse.ApiError -> {
                Result.Error(Exception(res.body.message.toString()))
            }
            is NetworkResponse.NetworkError -> {
                Result.Error(Exception("NetworkError"))
            }
            is NetworkResponse.UnknownError -> {
                Result.Error(Exception("UnknownError"))
            }
        }
    }

    override suspend fun getNews(page:Int): Result<List<Article>> {
        dao.getArticles2()?.let {
            it.forEach {
                Timber.d("from net ${it.articleId}")
            }

        }
        dao.getArticles(page*10-9)?.let {
            Timber.d("from dao ${it.size}")
            if (it.isNotEmpty()) {
                return Result.Success(it)
            }
        }
        val res = api.getNews(q = "Россия", key = "ffb0a70e60274c8d955b64776e69e100",pageSize = 10,page = page)
        return when (res) {
            is NetworkResponse.Success -> {
                val art:MutableList<Article> = mutableListOf()
                 res.body.articles.mapIndexedTo(art, { index, article ->
                     article.apply {
                         this.articleId = (page * 10-10) + index +1
                     }

                })
                Timber.d("from net ${res.body.articles.size}")
                dao.saveArticles(*art.toTypedArray())
                saveLastEnteredTime(Date().time)
                Result.Success(res.body.articles)
            }
            is NetworkResponse.ApiError -> {
                Result.Error(Exception(res.body.message.toString()))
            }
            is NetworkResponse.NetworkError -> {
                Result.Error(Exception("NetworkError"))
            }
            is NetworkResponse.UnknownError -> {
                Result.Error(Exception("UnknownError"))
            }
        }
    }

    override suspend fun getLastEnteredTime(): Long? {
        return dao.getLastEnteredTime()?.time
    }

    override suspend fun saveLastEnteredTime(time:Long){
        dao.saveLastEnteredTime(LastEnteredTime( time))
    }
}