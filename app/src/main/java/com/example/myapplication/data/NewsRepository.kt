package com.example.myapplication.data

import com.example.data.model.News
import com.example.myapplication.data.model.Result

interface NewsRepository {
    suspend fun getNews():Result<News>
}