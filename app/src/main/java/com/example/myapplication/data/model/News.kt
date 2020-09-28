package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class News (
	 val status : String,
	 val totalResults : Int,
	 val articles : List<Article>,
	 @PrimaryKey(autoGenerate = true)
	 val id:Int
)