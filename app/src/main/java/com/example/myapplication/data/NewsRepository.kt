package com.example.myapplication.data

import com.example.data.model.Article
import com.example.data.model.News
import com.example.myapplication.data.model.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(shouldUpdate:Boolean):Result<List<Article>>
    suspend fun getNews(page: Int): Result<List<Article>>
    fun observeDB(): Flow<List<Article>?>
    suspend fun getLastEnteredTime():Long?
    suspend fun saveLastEnteredTime(time: Long)
}