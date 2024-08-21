package com.example.androidassignment.data.repository

import com.example.androidassignment.data.source.remote.api.ApiService
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.model.toDomain
import com.example.androidassignment.domain.repository.TodoDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TodoDetailRepository {

    override fun getTodoDetail(id: Int): Flow<Todo> = flow {
        emit(apiService.getTodoDetail(id).toDomain())
    }
}
