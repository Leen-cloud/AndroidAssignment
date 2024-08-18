package com.example.androidassignment.domain.repository

import com.example.androidassignment.domain.model.Todo

interface TodoDetailRepository {
    suspend fun getTodoDetail(id: Int): Todo
}