package com.example.androidassignment.domain.repository

import com.example.androidassignment.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoDetailRepository {
    fun getTodoDetail(id: Int): Flow<Todo>
}
