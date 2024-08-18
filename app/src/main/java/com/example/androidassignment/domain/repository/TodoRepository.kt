package com.example.androidassignment.domain.repository

import com.example.androidassignment.domain.model.Todo

interface TodoRepository {
    suspend fun getTodos(): List<Todo>
    suspend fun saveTodos()
}