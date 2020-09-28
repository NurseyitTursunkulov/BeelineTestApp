package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.Article
import com.example.data.model.News
import com.example.myapplication.data.model.LastEnteredTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
//    @Insert
//    suspend fun saveNews(news: News)
//
//    @ExperimentalCoroutinesApi
//    @Query("SELECT * FROM News")
//    fun observeNews(): Flow<News>
//
//    @Query("SELECT * FROM News")
//    suspend fun getNews():News?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(vararg article: Article)

    @Query("DELETE FROM Article")
    suspend fun deleteAll()

    @Query("SELECT * FROM Article where articleId between :page and :page+10")
    suspend fun getArticles(page:Int):List<Article>?

    @Query("SELECT * FROM Article")
    suspend fun getArticles2():List<Article>?

    @Query("SELECT * FROM Article")
     fun observeArticles():Flow<List<Article>?>

    @Query("SELECT * FROM LastEnteredTime")
    suspend fun getLastEnteredTime():LastEnteredTime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastEnteredTime( lastEnteredTime: LastEnteredTime)
}