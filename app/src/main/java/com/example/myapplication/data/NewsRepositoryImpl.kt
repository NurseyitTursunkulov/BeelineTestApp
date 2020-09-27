package com.example.myapplication.data

import android.util.Log
import com.example.data.model.News
import com.example.data.util.network.NetworkResponse
import com.example.myapplication.data.local.NewsDao
import com.example.myapplication.data.model.Result
import timber.log.Timber

class NewsRepositoryImpl(
    private val api: FactServiceApi
//    ,
//    private val dao: NewsDao
) : NewsRepository {

    override suspend fun getNews(): Result<News> {
//        dao.getNews()?.let {
//            return Result.Success(it)
//        }
        val res = api.getNews(q = "bitcoin", key = "ffb0a70e60274c8d955b64776e69e100",pageSize = 10,page = 1)
        return when (res) {
            is NetworkResponse.Success -> {
//                dao.saveNews(res.body)
                Result.Success(res.body)
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

    override suspend fun getNews(page:Int): Result<News> {
//        dao.getNews()?.let {
//            return Result.Success(it)
//        }
        val res = api.getNews(q = "bitcoin", key = "ffb0a70e60274c8d955b64776e69e100",pageSize = 10,page = page)
        return when (res) {
            is NetworkResponse.Success -> {
//                dao.saveNews(res.body)
                Result.Success(res.body)
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

}