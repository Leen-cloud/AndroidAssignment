package com.example.androidassignment.data.source.remote.api

import com.example.androidassignment.domain.model.Todo
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    @GET("todos/{id}")
    suspend fun getTodoDetail(@Path("id") id: Int): Todo
}