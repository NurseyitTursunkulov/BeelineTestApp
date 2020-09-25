package com.example.myapplication.data

import com.example.data.model.News
import com.example.data.util.network.NetworkResponse
import retrofit2.http.*

interface FactServiceApi {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") status: String,
        @Query("apiKey") key: String
    ):NetworkResponse<News, Error>
}