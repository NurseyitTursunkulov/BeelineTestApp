package com.example.myapplication.data

import com.example.data.model.News
import com.example.data.util.network.NetworkResponse
import retrofit2.http.*

interface FactServiceApi {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("apiKey") key: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("q") q: String

    ):NetworkResponse<News, Error>
}