package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Article
import com.example.myapplication.data.model.LastEnteredTime

@Database(entities = arrayOf(Article::class,LastEnteredTime::class), version = 1, exportSchema = false)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun factsDao(): NewsDao

    companion object {
        private var INSTANCE: RoomDataBase? = null

        private val lock = Any()

        fun getInstance(context: Context): RoomDataBase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDataBase::class.java, "FactsDataBase.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}