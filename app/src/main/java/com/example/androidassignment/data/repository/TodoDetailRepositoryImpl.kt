package com.example.androidassignment.data.repository

import com.example.androidassignment.data.mapper.TodoMapper.toDomain
import com.example.androidassignment.data.source.remote.api.ApiService
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoDetailRepository
import javax.inject.Inject

class TodoDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService,

) : TodoDetailRepository {


    override suspend fun getTodoDetail(id: Int): Todo {
        val todo = apiService.getTodoDetail(id)
        return toDomain(todo)
    }


}


