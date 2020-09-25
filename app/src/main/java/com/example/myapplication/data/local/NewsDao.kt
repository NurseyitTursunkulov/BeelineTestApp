package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.News
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert
    suspend fun saveNews(news: News)

    @ExperimentalCoroutinesApi
    @Query("SELECT * FROM News")
    fun observeNews(): Flow<News>

    @Query("SELECT * FROM News")
    suspend fun getNews():News?

}