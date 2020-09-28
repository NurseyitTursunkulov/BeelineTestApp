package com.example.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article (
	@Embedded
	val source : Source,
	val author : String?,
	val title : String?,
	val description : String?,
	val url : String,
	val urlToImage : String?,
	val publishedAt : String?,
	val content : String?,
	@PrimaryKey()
	var articleId:Int
)