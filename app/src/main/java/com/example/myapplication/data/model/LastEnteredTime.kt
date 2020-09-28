package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class LastEnteredTime(
    val time: Long,
    @PrimaryKey val id: Int = 0
)