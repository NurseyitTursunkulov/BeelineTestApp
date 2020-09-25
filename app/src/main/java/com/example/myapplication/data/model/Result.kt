package com.example.myapplication.data.model

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

sealed class State<out T : Any> {
    object Idle : State<Nothing>()
    data class TriggerState<out T : Any>(val data: T): State<T>()
}
