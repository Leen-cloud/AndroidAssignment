package com.example.androidassignment.domain.repository

import com.example.androidassignment.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTodos(): Flow<List<Todo>>
}